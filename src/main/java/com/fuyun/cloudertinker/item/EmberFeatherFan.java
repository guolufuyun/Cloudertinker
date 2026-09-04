package com.fuyun.cloudertinker.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import slimeknights.tconstruct.shared.TinkerMaterials;
import twilightforest.block.LightableBlock;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.fan.FeatherFanFallCapability;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.network.TFPacketHandler;
import twilightforest.network.ThrowPlayerPacket;
import twilightforest.util.WorldUtil;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
public class EmberFeatherFan extends Item {

    private static final int EMBER_BURN_SECONDS = 5;

    /** 方块余烬配方：荆棘 → 烧焦荆棘 */
    public static final Map<Block, Block> EMBER_BURN_BLOCK_MAP = new HashMap<>();
    /** 物品余烬配方（掉落物形态）：黏钢锭 → 余烬黏液锭 等 */
    public static final Map<Item, Item> EMBER_BURN_ITEM_MAP = new HashMap<>();
    /** 生物余烬配方：巨钳甲虫 → 喷火甲虫 等 */
    public static final Map<EntityType<?>, EntityType<?>> EMBER_BURN_ENTITY_MAP = new HashMap<>();

    public static void registerEmberBurnRecipes() {
        if (ModList.get().isLoaded("twilightforest")) {
            EMBER_BURN_BLOCK_MAP.put(
                    TFBlocks.BROWN_THORNS.get(),
                    TFBlocks.BURNT_THORNS.get()
            );

            EMBER_BURN_BLOCK_MAP.put(
                    TFBlocks.GREEN_THORNS.get(),
                    TFBlocks.BURNT_THORNS.get()
            );

            EMBER_BURN_ENTITY_MAP.put(
                    TFEntities.PINCH_BEETLE.get(),
                    TFEntities.FIRE_BEETLE.get()
            );
        }

        EMBER_BURN_BLOCK_MAP.put(
                TinkerMaterials.slimesteel.get(),
                TinkerMaterials.cinderslime.get()
        );

        EMBER_BURN_ITEM_MAP.put(
                TinkerMaterials.slimesteel.getIngot(),
                TinkerMaterials.cinderslime.getIngot()
        );

        EMBER_BURN_ITEM_MAP.put(
                TinkerMaterials.slimesteel.getNugget(),
                TinkerMaterials.cinderslime.getNugget()
        );
    }

    public EmberFeatherFan(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        boolean flag = !player.onGround() && !player.isSwimming()
                && !player.getCapability(CapabilityList.FEATHER_FAN_FALLING)
                .map(FeatherFanFallCapability::getFalling).orElse(true);

        if (!level.isClientSide()) {
            int fanned = this.doFan(level, player);
            stack.hurtAndBreak(fanned + 1, player, (user) -> user.broadcastBreakEvent(hand));
            if (flag) {
                player.getCapability(CapabilityList.FEATHER_FAN_FALLING)
                        .ifPresent(cap -> cap.setFalling(true));
            }
        } else {
            if (player.isFallFlying()) {
                Vec3 look = player.getLookAngle();
                Vec3 movement = player.getDeltaMovement();
                player.setDeltaMovement(movement.add(
                        look.x() * 0.1D + (look.x() * 2.0D - movement.x()) * 0.5D,
                        (look.y() * 0.1D + (look.y() * 2.0D - movement.y()) * 0.5D) + 1.25D,
                        look.z() * 0.1D + (look.z() * 2.0D - movement.z()) * 0.5D));
            }
            if (flag) {
                player.setDeltaMovement(new Vec3(
                        player.getDeltaMovement().x() * 1.05F,
                        1.5F,
                        player.getDeltaMovement().z() * 1.05F));
            } else {
                AABB fanBox = this.getEffectAABB(player);
                Vec3 lookVec = player.getLookAngle();
                for (int i = 0; i < 30; i++) {
                    level.addParticle(ParticleTypes.FLAME,
                            fanBox.minX + level.getRandom().nextFloat() * (fanBox.maxX - fanBox.minX),
                            fanBox.minY + level.getRandom().nextFloat() * (fanBox.maxY - fanBox.minY),
                            fanBox.minZ + level.getRandom().nextFloat() * (fanBox.maxZ - fanBox.minZ),
                            lookVec.x(), lookVec.y(), lookVec.z());
                }
            }
            player.playSound(TFSounds.FAN_WHOOSH.get(), 1.0F + level.getRandom().nextFloat(),
                    level.getRandom().nextFloat() * 0.7F + 0.3F);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }

        player.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (this.emberBurn(level, pos)) {
            if (player != null) {
                player.playSound(TFSounds.LAMP_BURN.get(), 0.5F, 1.5F);
            }
            for (int i = 0; i < 10; i++) {
                float dx = pos.getX() + 0.5F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.75F;
                float dy = pos.getY() + 0.5F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.75F;
                float dz = pos.getZ() + 0.5F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.75F;
                level.addParticle(ParticleTypes.SMOKE, dx, dy, dz, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.FLAME, dx, dy, dz, 0.0D, 0.0D, 0.0D);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    // ==================== 孔雀羽毛扇逻辑 ====================

    private int doFan(Level level, Player player) {
        AABB fanBox = this.getEffectAABB(player);
        return this.fanBlocksInAABB(level, fanBox, player)
                + this.fanEntitiesInAABB(level, player, fanBox)
                + this.burnThornsInFanArea(level, fanBox);
    }

    private int fanEntitiesInAABB(Level level, Player player, AABB fanBox) {
        Vec3 moveVec = player.getLookAngle().scale(2);
        Item fan = player.getUseItem().getItem();
        int fannedEntities = 0;

        for (Entity entity : level.getEntitiesOfClass(Entity.class, fanBox)) {
            if (entity.isPushable() || entity instanceof ItemEntity || entity instanceof Projectile) {
                entity.setDeltaMovement(moveVec.x(), moveVec.y(), moveVec.z());
                fannedEntities++;
            }

            if (entity instanceof ServerPlayer pushedPlayer && pushedPlayer != player
                    && !pushedPlayer.isShiftKeyDown()) {
                TFPacketHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> pushedPlayer),
                        new ThrowPlayerPacket(moveVec.x(), moveVec.y(), moveVec.z()));
                player.getCooldowns().addCooldown(fan, 40);
                fannedEntities += 2;
            }

            // 余烬附加：对被扇飞的生物和掉落物
            if (entity instanceof LivingEntity living && living != player) {
                this.emberBurn(living);
            }
            if (entity instanceof ItemEntity itemEntity) {
                this.emberBurn(itemEntity);
            }
        }
        return fannedEntities;
    }

    private AABB getEffectAABB(Player player) {
        double range = 3.0D;
        double radius = 2.0D;
        Vec3 srcVec = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 lookVec = player.getLookAngle().scale(range);
        Vec3 destVec = srcVec.add(lookVec.x(), lookVec.y(), lookVec.z());
        return new AABB(destVec.x() - radius, destVec.y() - radius, destVec.z() - radius,
                destVec.x() + radius, destVec.y() + radius, destVec.z() + radius);
    }

    private int fanBlocksInAABB(Level level, AABB box, Player player) {
        int fan = 0;
        for (BlockPos pos : WorldUtil.getAllInBB(box)) {
            fan += this.fanBlock(level, pos, player);
        }
        return fan;
    }

    private int fanBlock(Level level, BlockPos pos, Player player) {
        int cost = 0;
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof FlowerBlock) {
            if (level.getRandom().nextInt(3) == 0) {
                if (!MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, pos, state, player))) {
                    level.destroyBlock(pos, true);
                    cost++;
                }
            }
        } else if (state.getBlock() instanceof AbstractCandleBlock
                && state.getValue(AbstractCandleBlock.LIT)) {
            AbstractCandleBlock.extinguish(null, state, level, pos);
        } else if (state.getBlock() instanceof LightableBlock lightable
                && state.getValue(LightableBlock.LIGHTING) != LightableBlock.Lighting.NONE) {
            lightable.extinguish(null, state, level, pos);
        }

        return cost;
    }

    // ==================== 余烬附加能力 ====================

    /**
     * JEI 适配：根据物品获取燃烧产物。
     * 优先查 ITEM_MAP，再自动将 BLOCK_MAP 转为物品映射（方块和掉落物不重复）
     */
    @javax.annotation.Nullable
    public static Item getEmberBurnResult(Item input) {
        Item direct = EMBER_BURN_ITEM_MAP.get(input);
        if (direct != null) return direct;
        Block block = input instanceof BlockItem bi ? bi.getBlock() : null;
        if (block != null) {
            Block resultBlock = EMBER_BURN_BLOCK_MAP.get(block);
            if (resultBlock != null) return resultBlock.asItem();
        }
        return null;
    }

    /** 传入生物：查 ENTITY_MAP 有配方则替换为新实体，否则点燃 */
    @SuppressWarnings("unchecked")
    private void emberBurn(LivingEntity entity) {
        EntityType<? extends LivingEntity> resultType =
                (EntityType<? extends LivingEntity>) EMBER_BURN_ENTITY_MAP.get(entity.getType());
        if (resultType != null) {
            Level level = entity.level();
            LivingEntity converted = resultType.create(level);
            if (converted != null) {
                converted.moveTo(entity.getX(), entity.getY(), entity.getZ(),
                        entity.getYRot(), entity.getXRot());

                level.addFreshEntity(converted);
                entity.discard();
            }
        } else {
            entity.setSecondsOnFire(EMBER_BURN_SECONDS);
        }
    }

    /** 传入掉落物：查 ITEM_MAP 替换物品，再兜底查 BLOCK_MAP（兼容方块掉落物），保留数量和 NBT */
    private void emberBurn(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        Item input = stack.getItem();
        Item result = EMBER_BURN_ITEM_MAP.get(input);
        if (result == null && input instanceof BlockItem bi) {
            Block resultBlock = EMBER_BURN_BLOCK_MAP.get(bi.getBlock());
            if (resultBlock != null) result = resultBlock.asItem();
        }
        if (result != null) {
            ItemStack converted = new ItemStack(result, stack.getCount());
            if (stack.hasTag()) {
                converted.setTag(stack.getTag().copy());
            }
            itemEntity.setItem(converted);
        }
    }

    /** 传入方块坐标：查 BLOCK_MAP 替换方块 */
    private boolean emberBurn(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block result = EMBER_BURN_BLOCK_MAP.get(state.getBlock());
        if (result != null) {
            level.setBlockAndUpdate(pos, result.withPropertiesOf(state));
            return true;
        }
        return false;
    }

    /** 在扇风区域内烧毁方块 */
    private int burnThornsInFanArea(Level level, AABB fanBox) {
        int burnt = 0;
        for (BlockPos pos : WorldUtil.getAllInBB(fanBox)) {
            if (this.emberBurn(level, pos)) {
                burnt++;
            }
        }
        return burnt;
    }
}