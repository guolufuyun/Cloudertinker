package com.fuyun.cloudertinker.entities;

import com.fuyun.cloudertinker.Modifiers.ToolModifiers.ChainSmash;
import com.fuyun.cloudertinker.tool.BlockAndChain;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.level.BlockEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;
import java.util.Objects;

/**
 * 链锤投掷实体（移植自 twilightforest:block_and_chain 的 {@code twilightforest.entity.ChainBlock}）。
 *
 * <p>出生方式：{@code BlockAndChain.use(...)} 右键发射，初速 {@link #LAUNCH_SPEED}；
 * 出链超过 {@link #MAX_CHAIN} 或命中目标后转为回程，回到 owner 2 格内自毁。
 * 服务端持有玩家手上那一格 {@link ItemStack} 的引用与其匠魂 {@link ToolStack} 活视图，
 * 命中时按匠魂 attack_damage 结算伤害并扣耐久。
 *
 * <p><b>破坏方块能力</b>由碎击词条 {@code cloudertinker:chainsmash} 提供：命中方块时以命中点为
 * 中心、按 {@link ChainSmash#getSmashRadius} 半径破坏方块，单次投掷最多
 * {@link ChainSmash#getMaxBlocks} 格（暮色为 12），且每级降低
 * {@link ChainSmash#getDamagePenalty} 的直接伤害。
 *
 * <p><b>命中伤害</b>不写死，也不自己调 {@code hurt}，而是走匠魂标准命中流程
 * {@code ToolAttackUtil#performAttack}（与 {@code TianTuiStar} 的写法一致）：
 * 于是 {@code MELEE_DAMAGE} / {@code MELEE_HIT} 等词条 hook、
 * 以及工具定义模块（本工具的 {@code tconstruct:circle_melee} 横扫）都会正常触发，
 * 伤害源也自动变成匠魂的 {@code tconstruct:thrown_tool}。
 * 基础伤害 = 手持近战等效攻击力 × {@link #THROWN_DAMAGE_MULTIPLIER}。
 *
 * <p><b>与暮色的差异（有意）</b>：
 * <ol>
 *     <li>伤害不写死 10，改为取手持近战等效攻击力并乘
 *         {@link #THROWN_DAMAGE_MULTIPLIER}，读不到才回退 {@link #DEFAULT_DAMAGE}；</li>
 *     <li>命中走匠魂 {@code ToolAttackUtil#performAttack} 而不是直接 {@code hurt}，
 *         这样链锤上的其他词条也会一起生效（耐久也由匠魂在该流程内扣）；</li>
 *     <li>{@code isReturning} 与初始弹道速度落盘（暮色只落盘 ItemStack，重载后回程公式会失效）；</li>
 *     <li>链节用纯数据 {@link ChainNode}，不引入 Forge PartEntity / getParts()；</li>
 *     <li>{@code defineSynchedData} 不调 super：1.20.1 里 {@code Entity.defineSynchedData()} 是抽象无参方法，
 *         {@code Projectile}/{@code ThrowableProjectile} 都未实现它、也没有 DATA_ITEM_STACK，
 *         写法与原版 {@code AbstractArrow#defineSynchedData} 一致；</li>
 *     <li>音效改用原版 {@link SoundEvents}，不新增 SoundEvent 注册。</li>
 * </ol>
 */
public class ChainBlock extends ThrowableProjectile implements IEntityAdditionalSpawnData {

    /** 初速（照抄暮色 shootFromRotation(..., 1.5F, 1.0F)） */
    public static final float LAUNCH_SPEED = 1.5F;

    /**
     * 锤头所在的部件槽位（tool_definition 里的第 1 个 part，当前是 {@code tconstruct:hammer_head}）。
     * 投掷实体的贴图与颜色就取这个槽的材质——对应匠魂浮漂取「箭头部件」材质的做法。
     */
    public static final int HAMMER_HEAD_PART = 0;

    /**
     * 「没有材质」哨兵。匠魂浮漂用的是 {@code MaterialId.UNKNOWN}，但本项目依赖的匠魂
     * 3.11.0.148 里 {@code MaterialId} 没有该常量（编译期报 cannot find symbol），
     * 因此改用同一个流派的自定义 id：查不到渲染信息时渲染器会退回基础贴图，效果与 UNKNOWN 等义。
     */
    public static final MaterialVariantId UNKNOWN_MATERIAL = MaterialVariantId.tryParse("cloudertinker:unknown");

    /** 重力（照抄暮色） */
    private static final float GRAVITY = 0.05F;

    /** 链条最大长度，超过即回程（照抄暮色，暮色里是 int 16） */
    private static final double MAX_CHAIN = 16.0D;

    /** 可破坏方块数量与半径的唯一来源是 {@link ChainSmash}（此处不再单独维护常量，避免两处漂移） */

    /** 命中面的反弹系数（照抄暮色） */
    private static final double BOUNCE = 0.6D;

    /** 读不到匠魂 attack_damage 时的兜底伤害（暮色固定 10） */
    private static final float DEFAULT_DAMAGE = 10.0F;

    // ==========================================================================
    // ★★★ 投掷命中伤害倍率 —— 想改「链锤砸人有多疼」，改这个数就行 ★★★
    //
    // 命中基础伤害 = 「手持该链锤时的近战攻击力」× THROWN_DAMAGE_MULTIPLIER
    //   · 「近战攻击力」取玩家 ATTACK_DAMAGE 属性，也就是左键砍同一下的伤害，
    //     里面已经包含：玩家基础攻击力 + 链锤的 attack_damage 统计
    //     （由 tool_definitions/block_and_chain.json 的 base_stats / multiply_stats
    //      与部件材料决定）+ 锋利等走属性加成的词条 + 力量效果；
    //   · 1.0 = 和近战一样疼；1.5 = 更狠；0.5 = 只有一半。
    //
    // 最终伤害还会在匠魂标准流程里继续被词条 hook 修改，其中碎击词条会再扣
    //   ChainSmash.DAMAGE_PENALTY_PER_LEVEL × 碎击等级
    // 完整公式：
    //   最终伤害 = 近战攻击力 × THROWN_DAMAGE_MULTIPLIER
    //              - ChainSmash.DAMAGE_PENALTY_PER_LEVEL × 碎击等级
    // ==========================================================================
    public static final float THROWN_DAMAGE_MULTIPLIER = 1.0F;

    private static final EntityDataAccessor<Boolean> HAND = SynchedEntityData.defineId(ChainBlock.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_FOIL = SynchedEntityData.defineId(ChainBlock.class, EntityDataSerializers.BOOLEAN);
    /**
     * 锤头部件的材质，决定客户端用哪张生成贴图与什么颜色渲染
     * （照抄匠魂浮漂实体 {@code CombatFishingHook} 的 MATERIAL 同步字段）。
     * 用 SynchedEntityData 而不是 spawn 包：出生与重进世界都会自动同步到客户端。
     */
    private static final EntityDataAccessor<MaterialVariantId> MATERIAL = SynchedEntityData.defineId(ChainBlock.class, MaterialVariantId.DATA_ACCESSOR);

    /** 5 个链节锚点（仅客户端计算/渲染） */
    public final ChainNode chain1 = new ChainNode();
    public final ChainNode chain2 = new ChainNode();
    public final ChainNode chain3 = new ChainNode();
    public final ChainNode chain4 = new ChainNode();
    public final ChainNode chain5 = new ChainNode();
    private final List<ChainNode> chainNodes = List.of(this.chain1, this.chain2, this.chain3, this.chain4, this.chain5);

    private boolean isReturning = false;
    private boolean hitEntity = false;
    /** 本次投掷已经破坏的方块总数，上限见 {@link ChainSmash#getMaxBlocks}（落盘） */
    private int blocksSmashed = 0;

    /** 玩家手上那一格的引用（不是副本）；只服务端有值 */
    @Nullable
    private ItemStack stack;
    /** {@code stack} 的匠魂活视图；只服务端持有，<b>绝不落盘</b> */
    @Nullable
    private ToolStack toolStack;

    /** 初始弹道速度，与 super.tick() 每 tick 覆写的 deltaMovement 分开保存（回程公式要用） */
    private double velX;
    private double velY;
    private double velZ;

    public ChainBlock(EntityType<? extends ChainBlock> type, Level level) {
        super(type, level);
    }

    public ChainBlock(EntityType<? extends ChainBlock> type, Level level, LivingEntity thrower, InteractionHand hand, ItemStack stack) {
        super(type, thrower, level);
        this.isReturning = false;
        this.stack = stack;
        if (stack.getItem() instanceof IModifiable) {
            try {
                this.toolStack = ToolStack.from(stack);
            } catch (Exception ignored) {
                this.toolStack = null;
            }
        }
        this.setHand(hand);
        this.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0.0F, LAUNCH_SPEED, 1.0F);
        this.getEntityData().set(IS_FOIL, stack.hasFoil());
        // 取锤头部件的材质同步给客户端渲染（拿不到匠魂数据时保持 UNKNOWN = 用基础贴图）
        this.setMaterial(this.toolStack == null
                ? UNKNOWN_MATERIAL
                : this.toolStack.getMaterial(HAMMER_HEAD_PART).getVariant());
    }

    /** 链节锚点（渲染器按 “当前/上一 tick” 插值），恒定非空 */
    public List<ChainNode> getChainNodes() {
        return this.chainNodes;
    }

    public boolean isFoil() {
        return this.getEntityData().get(IS_FOIL);
    }

    public boolean isReturning() {
        return this.isReturning;
    }

    /** 当前显示的锤头材质（客户端渲染用；照抄匠魂 CombatFishingHook#getMaterial） */
    public MaterialVariantId getMaterial() {
        return this.getEntityData().get(MATERIAL);
    }

    /** 设置锤头材质（服务端出生时 / 落盘读取时调用） */
    public void setMaterial(MaterialVariantId material) {
        this.getEntityData().set(MATERIAL, material);
    }

    /**
     * 当前能否破坏方块：需要碎击词条（{@code cloudertinker:chainsmash}）且投掷者没有挖掘疲劳
     * （与暮色 destruction 的判定等义）。
     *
     * <p>刻意做成「每次询问时现算」而不是构造器缓存一个 boolean：
     * 缓存值在区块重载（{@code readAdditionalSaveData} 重建 ItemStack）后会失真，
     * 表现为「重进世界后碎击失效」。
     */
    public boolean canSmashBlocks() {
        ToolStack tool = this.tool();
        if (tool == null || !ChainSmash.canSmash(tool)) {
            return false;
        }
        return !(this.getOwner() instanceof LivingEntity owner && owner.hasEffect(MobEffects.DIG_SLOWDOWN));
    }

    private void setHand(InteractionHand hand) {
        this.getEntityData().set(HAND, hand == InteractionHand.MAIN_HAND);
    }

    public InteractionHand getHand() {
        return this.getEntityData().get(HAND) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float accuracy) {
        super.shoot(x, y, z, speed, accuracy);
        // 记下初始弹道速度，回程公式要与 super.tick() 每 tick 覆写的 deltaMovement 分开
        this.velX = this.getDeltaMovement().x();
        this.velY = this.getDeltaMovement().y();
        this.velZ = this.getDeltaMovement().z();
    }

    @Override
    protected float getGravity() {
        return GRAVITY;
    }

    /**
     * 投掷命中的「基础伤害」= 手持近战等效攻击力 × {@link #THROWN_DAMAGE_MULTIPLIER}。
     *
     * <p>近战等效攻击力由 {@code ToolAttackUtil#getToolAttribute} 取得（与
     * {@code ToolAttackContext.Builder#toolAttributes} 调用的完全是同一个方法）：主手时就是玩家
     * ATTACK_DAMAGE 属性，因此锋利等走属性加成的词条、力量效果都会被算进去；副手/非主手时
     * 匠魂会临时把该工具当作主手计算（旧写法 {@code #getAttributeAttackDamage} 已标记
     * {@code @Deprecated(forRemoval = true)}，故改用非弃用的新 API）。
     *
     * <p>拿到的基础伤害会喂给 {@code ToolAttackContext.Builder#baseDamage}，之后匠魂依次跑
     * {@code MELEE_DAMAGE} hook（碎击词条在这里扣 -1.5×等级）再结算真实伤害。
     * <b>所以这里不要再扣一次碎击惩罚</b>，否则会双重扣减。
     *
     * <p>想调整「击中伤害倍率」请改 {@link #THROWN_DAMAGE_MULTIPLIER}（那里有详细注释）。
     */
    public float getAttackDamage(@Nullable LivingEntity owner) {
        ToolStack tool = this.tool();
        if (tool == null) {
            // 读不到匠魂数据（理论上不会发生）：退回暮色的固定伤害，但仍尊重倍率
            return DEFAULT_DAMAGE * THROWN_DAMAGE_MULTIPLIER;
        }
        float damage;
        if (owner != null) {
            damage = ToolAttackUtil.getToolAttribute(tool, owner, Attributes.ATTACK_DAMAGE, tool.getStats().get(ToolStats.ATTACK_DAMAGE));
        } else {
            damage = tool.getStats().get(ToolStats.ATTACK_DAMAGE);
        }
        if (damage <= 0.0F) {
            damage = DEFAULT_DAMAGE;
        }
        return Math.max(0.0F, damage * THROWN_DAMAGE_MULTIPLIER);
    }

    @Nullable
    private ToolStack tool() {
        if (this.stack == null || this.stack.isEmpty() || !(this.stack.getItem() instanceof IModifiable)) {
            return null;
        }
        if (this.toolStack == null) {
            try {
                this.toolStack = ToolStack.from(this.stack);
            } catch (Exception e) {
                return null;
            }
        }
        return this.toolStack;
    }

    /** 扣耐久：优先进匠魂通路，匠魂不可用时退回原版（待运行期验证） */
    private void damageTool(int amount, LivingEntity holder) {
        if (amount <= 0) {
            return;
        }
        ToolStack tool = this.tool();
        if (tool != null) {
            ToolDamageUtil.damageAnimated(tool, amount, holder);
            return;
        }
        if (this.stack != null && !this.stack.isEmpty()) {
            this.stack.hurtAndBreak(amount, holder, entity -> entity.broadcastBreakEvent(this.getHand()));
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide() || result.getEntity() == this.getOwner()) {
            return;
        }

        // 多部件生物（如九头蛇）打中的是 PartEntity，伤害要落到本体上
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part && part.getParent() instanceof LivingEntity parent) {
            target = parent;
        }
        if (!(target instanceof LivingEntity livingTarget)) {
            return;
        }
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;

        // 正确破盾（照抄暮色）
        if (result.getEntity() instanceof Player player && player.isUsingItem()
                && player.getUseItem().canPerformAction(ToolActions.SHIELD_BLOCK)) {
            player.getUseItem().hurtAndBreak(5, player, entity -> entity.broadcastBreakEvent(player.getUsedItemHand()));
            player.disableShield(true);
        }

        ToolStack tool = this.tool();
        boolean didHit;
        if (tool != null && owner != null) {
            // ★ 走匠魂标准命中流程（与 TianTuiStar 的写法一致）：
            //   · MELEE_DAMAGE hook → 链锤上所有加/减伤词条（含碎击惩罚）都会生效；
            //   · MELEE_HIT hook + 工具定义模块（本工具是 tconstruct:circle_melee）→ 横扫/特效一起触发；
            //   · .projectile(this) → 伤害源是匠魂的 tconstruct:thrown_tool，而不是普通近战；
            //   · 耐久也由 performAttack 内部按匠魂规则扣（投掷走 ToolDamageUtil.damage，
            //     因此这里不再手动 damageTool，避免双重扣耐久）。
            ToolAttackContext context = ToolAttackContext.attacker(owner)
                    .target(livingTarget)
                    .slot(Util.getSlotType(this.getHand()), this.getHand())
                    .projectile(this)
                    // 1.0 = 视为满蓄力，不做 0.2+0.8x² 的蓄力衰减
                    .cooldown(1.0F)
                    // 基础伤害 = 手持近战等效攻击力 × THROWN_DAMAGE_MULTIPLIER
                    .baseDamage(this.getAttackDamage(owner))
                    // 说明：不调用 .sound(...)，让匠魂按蓄力/疾跑自行挑一个近战音在「投掷者位置」播放
                    //（performAttack 的音效位置固定为攻击者，无法改到命中点）；
                    // 命中点的音效由下面的 playSound 播放。
                    .build();
            didHit = ToolAttackUtil.performAttack(tool, context);
        } else {
            // 兜底：拿不到匠魂数据 / 投掷者不是生物时，退回原版投掷伤害
            float damage = this.getAttackDamage(owner);
            didHit = damage > 0.0F && livingTarget.hurt(this.damageSources().thrown(this, this.getOwner()), damage);
        }

        if (didHit) {
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, this.random.nextFloat());
            // 命中后加速老化，让它更快回到玩家手上
            this.hitEntity = true;
            this.isReturning = true;
            this.tickCount += 60;
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.level().isClientSide() || this.level().isEmptyBlock(result.getBlockPos())) {
            return;
        }

        // 链锤是武器而不是采掘工具：tool_definition 里没有 tconstruct:is_effective 模块，
        // 所以 ModifiableItem#isCorrectToolForDrops 恒为 false —— 撞到任何方块都反弹回程。
        if (this.stack == null || !this.stack.isCorrectToolForDrops(this.level().getBlockState(result.getBlockPos()))) {
            if (!this.isReturning && !this.hitEntity) {
                this.playSound(SoundEvents.ANVIL_LAND, 0.125F, this.random.nextFloat());
                this.gameEvent(GameEvent.HIT_GROUND);
            }

            this.isReturning = true;

            // 反弹（照抄暮色 :166-204，含其“先 *= bounce 再 *= -bounce”的原样行为）
            this.velX *= BOUNCE;
            this.velY *= BOUNCE;
            this.velZ *= BOUNCE;

            switch (result.getDirection()) {
                case DOWN -> {
                    if (this.velY > 0) this.velY *= -BOUNCE;
                }
                case UP -> {
                    if (this.velY < 0) this.velY *= -BOUNCE;
                }
                case NORTH -> {
                    if (this.velZ > 0) this.velZ *= -BOUNCE;
                }
                case SOUTH -> {
                    if (this.velZ < 0) this.velZ *= -BOUNCE;
                }
                case WEST -> {
                    if (this.velX > 0) this.velX *= -BOUNCE;
                }
                case EAST -> {
                    if (this.velX < 0) this.velX *= -BOUNCE;
                }
            }
        }

        // 碎击词条：破坏以「命中点」为中心的方块（暮色 destruction 附魔的等价能力）
        if (this.canSmashBlocks() && this.stack != null) {
            ToolStack tool = this.tool();
            int level = tool == null ? 0 : ChainSmash.getLevel(tool);
            int maxBlocks = ChainSmash.getMaxBlocks(level);
            int budget = maxBlocks - this.blocksSmashed;
            if (budget > 0) {
                this.blocksSmashed += this.smashBlocksAround(
                        result.getLocation(), ChainSmash.getSmashRadius(level), budget);
            }
            // 本次投掷的破坏额度用光 → 立刻回程（照抄暮色 MAX_SMASH 的行为）
            if (maxBlocks > 0 && this.blocksSmashed >= maxBlocks) {
                this.isReturning = true;
                if (this.tickCount < 60) {
                    this.tickCount += 60;
                }
            }
        }
    }

    /**
     * 破坏以 {@code center} 为中心、半径 {@code radius} 的立方体内的方块，最多 {@code budget} 格，
     * 返回实际破坏数量。
     *
     * <p>移植自暮色 {@code ChainBlock#affectBlocksInAABB}（原实现依赖暮色自己的
     * {@code WorldUtil.getAllInBB}，本工程无该类，故手写等价遍历），但去掉了两处会「一个方块都砸不掉」的判定：
     * <ol>
     *     <li>不再要求 {@code stack.isCorrectToolForDrops(state)} —— 链锤没有
     *         {@code tconstruct:is_effective} 模块，该判定恒为 false（这是早期版本碎击完全失效的原因）；</li>
     *     <li>不再调用 {@code ForgeEventFactory.doPlayerHarvestCheck} —— 传入的 canHarvest 同上恒为 false，
     *         等于把全部方块都过滤掉；保护类模组仍可通过下面的 {@link BlockEvent.BreakEvent} 否决破坏。</li>
     * </ol>
     *
     * <p>以「命中点」而不是「实体包围盒中心」为基准：暮色为了抵消实体中心偏移，曾在 X 轴上硬减 0.5
     * （导致撞东西两侧不对称），以命中点为球心后该 hack 不再需要，破坏范围天然对称。
     */
    private int smashBlocksAround(Vec3 center, double radius, int budget) {
        if (budget <= 0 || radius <= 0.0D || !(this.getOwner() instanceof Player player) || this.stack == null) {
            return 0;
        }
        ToolStack tool = this.tool();
        boolean creative = player.getAbilities().instabuild;
        AABB box = new AABB(center, center).inflate(radius);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int broken = 0;

        for (int x = Mth.floor(box.minX); x <= Mth.floor(box.maxX); x++) {
            for (int y = Mth.floor(box.minY); y <= Mth.floor(box.maxY); y++) {
                for (int z = Mth.floor(box.minZ); z <= Mth.floor(box.maxZ); z++) {
                    pos.set(x, y, z);
                    BlockState state = this.level().getBlockState(pos);
                    // 统一的可破坏判定（空气 / 基岩 / 流体 / 容器），见 ChainSmash#isSmashable
                    if (!ChainSmash.isSmashable(this.level(), pos, state,tool)) {
                        continue;
                    }
                    // 保护类模组 / 领地插件通过该事件否决破坏
                    if (MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(this.level(), pos, state, player))) {
                        continue;
                    }
                    // 方块实体要在 destroyBlock 之前取，destroyBlock 之后它已经被移除了
                    BlockEntity blockEntity = this.level().getBlockEntity(pos);
                    if (!this.level().destroyBlock(pos, false)) {
                        continue;
                    }
                    if (!creative) {
                        state.getBlock().playerDestroy(this.level(), player, pos.immutable(), state, blockEntity, this.stack);
                    }
                    broken++;
                    if (broken >= budget) {
                        return broken;
                    }
                }
            }
        }
        return broken;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            // 先存档旧坐标，再写新坐标（渲染插值顺序不能反）
            for (ChainNode node : this.chainNodes) {
                node.updateLastPos();
            }
            if (this.getOwner() != null) {
                Vec3 handVec = this.getOwner().getLookAngle().yRot(this.getHand() == InteractionHand.MAIN_HAND ? -0.4F : 0.4F);

                double sx = this.getOwner().getX() + handVec.x();
                double sy = this.getOwner().getY() + handVec.y() - 0.4F + this.getOwner().getEyeHeight();
                double sz = this.getOwner().getZ() + handVec.z();

                double ox = sx - this.getX();
                double oy = sy - this.getY() - 0.25F;
                double oz = sz - this.getZ();

                this.chain1.setPos(sx - ox * 0.05D, sy - oy * 0.05D, sz - oz * 0.05D);
                this.chain2.setPos(sx - ox * 0.25D, sy - oy * 0.25D, sz - oz * 0.25D);
                this.chain3.setPos(sx - ox * 0.45D, sy - oy * 0.45D, sz - oz * 0.45D);
                this.chain4.setPos(sx - ox * 0.65D, sy - oy * 0.65D, sz - oz * 0.65D);
                this.chain5.setPos(sx - ox * 0.85D, sy - oy * 0.85D, sz - oz * 0.85D);
            }
            return;
        }

        // ---- 服务端 ----
        if (this.getOwner() == null) {
            this.discard();
            return;
        }

        double distToOwner = this.distanceTo(this.getOwner());
        if (!this.isReturning && distToOwner > MAX_CHAIN) {
            this.isReturning = true;
        }
        if (!this.isReturning) {
            return;
        }

        if (distToOwner < 2.0D) {
            if (this.getOwner() instanceof LivingEntity owner && this.blocksSmashed > 0) {
                this.damageTool(Math.min(this.blocksSmashed, 3), owner);
            }
            this.discard();
            return;
        }

        if (!(this.getOwner() instanceof LivingEntity returnTo)) {
            this.discard();
            return;
        }

        Vec3 back = new Vec3(returnTo.getX(), returnTo.getY() + returnTo.getEyeHeight(), returnTo.getZ())
                .subtract(this.position()).normalize();
        double age = Math.min(this.tickCount * 0.03D, 1.0D);

        // 回程速度与弹道速度分开混合（照抄暮色 :304-308）
        this.setDeltaMovement(new Vec3(
                this.velX * (1.0D - age) + back.x() * 2.0D * age,
                this.velY * (1.0D - age) + back.y() * 2.0D * age - this.getGravity(),
                this.velZ * (1.0D - age) + back.z() * 2.0D * age
        ));
    }

    @Override
    protected void defineSynchedData() {
        // 注意：1.20.1 的 Entity.defineSynchedData() 是「抽象无参」方法，Projectile/ThrowableProjectile
        // 都没有实现它、也没有定义 DATA_ITEM_STACK，因此这里不能调 super（会编译报错）。
        // 与 AbstractArrow.defineSynchedData()（原版 AbstractArrow.java:106）一致：直接定义自己的同步字段。
        this.getEntityData().define(HAND, true);
        this.getEntityData().define(IS_FOIL, false);
        this.getEntityData().define(MATERIAL, UNKNOWN_MATERIAL);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.getOwner() instanceof LivingEntity thrower) {
            ItemStack useItem = thrower.getUseItem();
            if (!useItem.isEmpty() && useItem.getItem() instanceof BlockAndChain) {
                thrower.stopUsingItem();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("BlockAndChainStack", Tag.TAG_COMPOUND)) {
            this.stack = ItemStack.of(tag.getCompound("BlockAndChainStack"));
            this.toolStack = null;
        }
        // 暮色没有落盘 isReturning，重载后回程判定会重来一遍；这里补上
        this.isReturning = tag.getBoolean("IsReturning");
        this.hitEntity = tag.getBoolean("HitEntity");
        this.blocksSmashed = tag.getInt("BlocksSmashed");
        this.velX = tag.getDouble("VelX");
        this.velY = tag.getDouble("VelY");
        this.velZ = tag.getDouble("VelZ");
        // 锤头材质落盘，重进世界后贴图/颜色不变（照抄浮漂的 material 字段）
        if (tag.contains("material", Tag.TAG_STRING)) {
            this.setMaterial(Objects.requireNonNullElse(MaterialVariantId.tryParse(tag.getString("material")), UNKNOWN_MATERIAL));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.stack != null && !this.stack.isEmpty()) {
            tag.put("BlockAndChainStack", this.stack.save(new CompoundTag()));
        }
        tag.putBoolean("IsReturning", this.isReturning);
        tag.putBoolean("HitEntity", this.hitEntity);
        tag.putInt("BlocksSmashed", this.blocksSmashed);
        tag.putDouble("VelX", this.velX);
        tag.putDouble("VelY", this.velY);
        tag.putDouble("VelZ", this.velZ);
        tag.putString("material", this.getMaterial().toString());
    }

    // 客户端能画出链条、拿到手部/箔光状态的唯一前提
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.getOwner() != null ? this.getOwner().getId() : -1);
        buffer.writeBoolean(this.getHand() == InteractionHand.MAIN_HAND);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        Entity owner = this.level().getEntity(buffer.readInt());
        if (owner instanceof LivingEntity) {
            this.setOwner(owner);
        }
        this.setHand(buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }
}
