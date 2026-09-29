package com.fuyun.cloudertinker.tool;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.entities.ChainBlock;
import com.fuyun.cloudertinker.register.CloudertinkerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.UUID;

/**
 * 链锤（block_and_chain）：匠魂模块化工具，慢速重型近战武器 + 可投掷链锤。
 * 近战伤害与耐久消耗完全交给匠魂主伤害通路（ToolAttackUtil / HarvestLogic + ToolHooks），
 * 因此这里不覆写 hurtEnemy / mineBlock 去手动 hurtAndBreak。
 *
 * <p>投掷逻辑移植自暮色 {@code twilightforest.item.ChainBlockItem}：
 * 右键发射 {@link ChainBlock}，把实体 UUID 写进工具数据，链子在外面时不允许再投掷。
 *
 * <p><b>右键与匠魂交互的关系（待运行期验证）</b>：匠魂的右键交互走
 * {@code ModifierHooks.GENERAL_INTERACT → GeneralInteractionModifierHook.onToolUse(...)}，
 * 由 {@code tconstruct:modifiable/interactable/right} 标签门控。本类<b>先调用 {@code super.use(...)}</b>，
 * 只有它返回 {@link InteractionResult#PASS}（即没有词条接管这次右键）时才走链锤自己的发射逻辑，
 * 以免截断匠魂默认交互/其他词条的右键。若运行期发现 {@code super.use} 总是返回非 PASS，
 * 本工具的投掷会失效，需要改为“自己派发 GENERAL_INTERACT 后再兜底”。
 *
 * <p><b>{@link #getUseDuration}/{@link #getUseAnimation} 的决定</b>：按暮色返回固定 72000 / {@link UseAnim#BLOCK}。
 * 无法在本环境读到 TConstruct sources jar，所以依据“匠魂右键 hook 已由 super.use() 先过一遍、
 * 且本工具当前没有任何右键词条”判断：固定值不会额外损失词条行为（词条右键一旦存在，
 * 应在 P4 把这两个方法改成按 {@link #CHAIN_ENTITY_KEY} 是否存在来分支）。标记为待运行期验证。
 */
public class BlockAndChain extends ModifiableItem {

    /** 投掷中实体 UUID 的 key：匠魂 PersistentData（存的是 UUID 字符串）。路径名沿用暮色的 chainEntity。 */
    public static final ResourceLocation CHAIN_ENTITY_KEY = Cloudertinker.getResource("chain_entity");

    public BlockAndChain(Item.Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // ① 先让匠魂处理右键（词条右键优先）
        InteractionResultHolder<ItemStack> superResult = super.use(level, player, hand);
        if (superResult.getResult() != InteractionResult.PASS) {
            return superResult;
        }

        // ② 链子还在外面：不重复投掷（暮色 ChainBlockItem 同此行为）
        if (isThrown(level, stack)) {
            return InteractionResultHolder.pass(stack);
        }

        // ③ 断掉的匠魂工具不能投掷（与匠魂工具使用规则一致）
        if (isBroken(stack)) {
            return InteractionResultHolder.fail(stack);
        }

        // ④ 发射音效：暮色 BLOCK_AND_CHAIN_FIRED 的原版来源就是 ARROW_SHOOT
        if (level.isClientSide()) {
            player.playSound(SoundEvents.ARROW_SHOOT, 0.5F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
            // 让客户端进入 UseAnim.BLOCK 使用姿态（与暮色一致；服务端也会 startUsingItem）
            player.startUsingItem(hand);
            return InteractionResultHolder.success(stack);
        }

        ChainBlock launched = new ChainBlock(CloudertinkerEntity.chain_block.get(), level, player, hand, stack);
        level.addFreshEntity(launched);
        setThrownEntity(stack, launched);
        // ChainBlock.remove() 会检查 owner 正在使用链锤并 stopUsingItem()
        player.startUsingItem(hand);
        return InteractionResultHolder.success(stack);
    }

    /**
     * 链子是否还在外面。客户端只看工具数据（NBT 会同步给客户端），
     * 服务端额外确认实体真的还存在（避免实体丢失后永久无法再投掷）。
     */
    public static boolean isThrown(Level level, ItemStack stack) {
        if (getThrownUuid(stack) == null) {
            return false;
        }
        return level.isClientSide() || getThrownEntity(level, stack) != null;
    }

    @Nullable
    public static UUID getThrownUuid(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof IModifiable)) {
            return null;
        }
        try {
            String raw = ToolStack.from(stack).getPersistentData().getString(CHAIN_ENTITY_KEY);
            if (raw == null || raw.isEmpty()) {
                return null;
            }
            return UUID.fromString(raw);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static ChainBlock getThrownEntity(Level level, ItemStack stack) {
        if (level instanceof ServerLevel server) {
            UUID id = getThrownUuid(stack);
            if (id != null) {
                Entity entity = server.getEntity(id);
                if (entity instanceof ChainBlock chainBlock) {
                    return chainBlock;
                }
            }
        }
        return null;
    }

    private static void setThrownEntity(ItemStack stack, ChainBlock chainBlock) {
        try {
            ToolStack.from(stack).getPersistentData().putString(CHAIN_ENTITY_KEY, chainBlock.getUUID().toString());
        } catch (Exception ignored) {
            // 工具数据不可用时只能放弃记录；不影响实体本身
        }
    }

    private static void clearThrownEntity(ItemStack stack) {
        try {
            ToolStack.from(stack).getPersistentData().remove(CHAIN_ENTITY_KEY);
        } catch (Exception ignored) {
            // 同上
        }
    }

    private static boolean isBroken(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof IModifiable)) {
            return false;
        }
        try {
            return ToolStack.from(stack).isBroken();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity holder, int slot, boolean isSelected) {
        // 必须调 super：匠魂在此派发 InventoryTickModifierHook（Heatsword / Ravenfeather / Hyperplasia 等词条依赖它）
        super.inventoryTick(stack, level, holder, slot, isSelected);
        if (!level.isClientSide() && getThrownUuid(stack) != null && getThrownEntity(level, stack) == null) {
            clearThrownEntity(stack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }
}
