package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.data.ModifierIds;

/**
 * 碎击（chain smash）：链锤命中时破坏命中点周围的方块，代价是每级降低直接伤害。
 *
 * <p><b>为什么破坏逻辑不放在本类里</b>：方块破坏需要知道「打在哪个坐标」，只有投掷实体
 * {@link com.fuyun.cloudertinker.entities.ChainBlock} 知道命中点，所以实体负责遍历与破坏，
 * 本类只做两件事：① 提供全部可调数值与判定；② 通过 {@link ModifierHooks#MELEE_DAMAGE} 扣除伤害。
 *
 * <p><b>数值总览（想调整手感只需要改下面的常量）</b>
 * <table summary="碎击等级对应的数值">
 *     <caption>碎击各等级数值</caption>
 *     <tr><th>等级</th><th>可破坏方块总数</th><th>破坏半径</th><th>直接伤害惩罚</th></tr>
 *     <tr><td>1</td><td>4</td><td>0.6 格</td><td>-1.5</td></tr>
 *     <tr><td>2</td><td>8</td><td>1.2 格</td><td>-3.0</td></tr>
 *     <tr><td>3</td><td>12（触顶）</td><td>1.8 格</td><td>-4.5</td></tr>
 * </table>
 *
 * <p><b>伤害惩罚同时作用于近战与投掷</b>：投掷命中已经改走
 * {@code ToolAttackUtil.performAttack}（匠魂标准命中流程），因此
 * {@link #getMeleeDamage} 会被调用；近战攻击也走同一条 hook。
 * 投掷伤害的「基础值 / 倍率」在
 * {@link com.fuyun.cloudertinker.entities.ChainBlock#THROWN_DAMAGE_MULTIPLIER} 处调整，
 * 最终公式为：
 * <pre>
 * 投掷最终伤害 = (手持近战等效攻击力 × ChainBlock.THROWN_DAMAGE_MULTIPLIER)
 *              - ChainSmash.DAMAGE_PENALTY_PER_LEVEL × 碎击等级
 * </pre>
 */
public class ChainSmash extends Modifier implements MeleeDamageModifierHook {

    // ==================== 可调数值 ====================

    /** 每级降低的直接伤害（近战与投掷都会生效，单位 = 半颗心） */
    public static final float DAMAGE_PENALTY_PER_LEVEL = 0F;

    /** 每级允许破坏的方块数量 */
    public static final int BLOCKS_PER_LEVEL = 4;

    /**
     * 单次投掷最多破坏的方块总数硬上限。
     * 达到该上限后链锤立刻回程（照抄暮色 MAX_SMASH 的行为）。
     */
    public static final int MAX_BLOCKS = 12;

    /**
     * 破坏半径 = 等级 × 该值（单位：格）。
     * 1 级 0.6 / 2 级 1.2 / 3 级 1.8，即命中点周围一个立方体。
     * 想「一下就砸一大片」就把这个值调大（注意同时受 {@link #BLOCKS_PER_LEVEL} 与
     * {@link #MAX_BLOCKS} 限制，实际破坏数量不会超过上限）。
     */
    public static final double SMASH_RADIUS_PER_LEVEL = 0.6D;

    /**
     * 是否允许破坏带方块实体的方块（箱子、熔炉、机器等）。
     * 默认 {@code false}：破坏方块时不会掉落内容物，若允许砸箱子会直接毁掉里面的东西。
     */
    public static final boolean SMASH_BLOCK_ENTITIES = false;

    // ==================== 判定与取值 ====================

    /** 词条等级（0 = 未安装） */
    public static int getLevel(IToolStackView tool) {
        return tool.getModifierLevel(CloudertinkerModifiers.chainsmash.getId());
    }

    /** 每级降低的直接伤害（供注释/显示使用；实际扣减在 {@link #getMeleeDamage} 内） */
    public static float getDamagePenalty(int level) {
        return level * DAMAGE_PENALTY_PER_LEVEL;
    }

    /** 给定等级下一次投掷最多破坏多少方块 */
    public static int getMaxBlocks(int level) {
        if (level <= 0) {
            return 0;
        }
        return Math.min(level * BLOCKS_PER_LEVEL, MAX_BLOCKS);
    }

    /** 给定等级的破坏半径（格） */
    public static double getSmashRadius(int level) {
        return  SMASH_RADIUS_PER_LEVEL;
    }

    /** 是否允许破坏方块 */
    public static boolean canSmash(IToolStackView tool) {
        return getLevel(tool) > 0;
    }

    /**
     * 单个方块能否被碎击破坏。
     *
     * <p><b>这里刻意不使用 {@code ItemStack#isCorrectToolForDrops}</b>：链锤是武器不是采掘工具，
     * 它的 tool_definition 里没有 {@code tconstruct:is_effective} 模块，
     * 而 {@code ModifiableItem#isCorrectToolForDrops} 会直接委托给
     * {@code IsEffectiveToolHook.isEffective}（缺少该模块时恒为 false）。
     * 早期版本用这个判定过滤方块，导致「装了 3 级碎击也一个方块都砸不掉」。
     * 碎击是暮色 destruction 附魔的等价物，本来就该砸穿工具挖不动的方块，因此改为硬度判定。
     *
     * <p>掉落物交给 {@code Block#playerDestroy} 按原版战利品表 + 该工具判定，
     * 所以链锤砸石头多数情况下只破坏、不掉落——这正是「碎击」应有的观感。
     */
    public static int getTargetHarvestLevel(int modifierLevel) {
        if (modifierLevel <= 0) return -1;
        return Math.min(modifierLevel - 1, 4);
    }
    public static Tier getTargetTier(int modifierLevel) {
        return switch (modifierLevel) {
            case 1 -> Tiers.WOOD;
            case 2 -> Tiers.STONE;
            case 3 -> Tiers.IRON;
            case 4 -> Tiers.DIAMOND;
            default -> Tiers.NETHERITE;
        };
    }
    public static boolean isSmashable(Level level, BlockPos pos, BlockState state, IToolStackView tool) {
        int modifierLevel = tool.getModifierLevel(CloudertinkerModifiers.chainsmash.getId());
        if (state.isAir()) {
            return false;
        }
        // 硬度 < 0 表示不可破坏（基岩、屏障、命令方块等）
        if (state.getDestroySpeed(level, pos) < 0.0F) {
            return false;
        }
        // 不破坏流体方块
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        // 默认跳过带方块实体的方块，避免连带毁掉容器内容
        if (!SMASH_BLOCK_ENTITIES && level.getBlockEntity(pos) != null) {
            return false;
        }
        // 方块必须属于镐 / 锄 / 锹 / 斧可挖掘类型（照抄暮色 isCorrectToolForDrops 的过滤）
        boolean mineable = state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_HOE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_AXE);
        if (!mineable) {
            return false;
        }
        // Forge 正规 tier 判定：碎击等级对应的工具 tier 是否够挖
        return TierSortingRegistry.isCorrectTierForDrops(getTargetTier(modifierLevel), state);
    }

    // ==================== 词条本体 ====================

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }

    @Override
    public float getMeleeDamage(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, @NotNull ToolAttackContext context, float baseDamage, float damage) {
        Player player = context.getPlayerAttacker();
        if (player != null && player.getAbilities().instabuild) {
            return damage;
        }
        return Math.max(0.0F, damage - getDamagePenalty(modifier.getLevel()));
    }
}
