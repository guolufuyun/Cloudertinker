package com.fuyun.cloudertinker.Modifiers.BaseModifiers;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class made_in_heaven extends NoLevelsModifier implements MeleeHitModifierHook , ProjectileHitModifierHook {
    private static final int RADIUS = 16; // 影响范围半径
    private static final int MULTIPLIER = 100; // 随机刻倍率
    public  made_in_heaven() {
            MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT,ModifierHooks.MELEE_HIT);
    }


    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null) {
            AttributeInstance attribute = context.getLivingTarget().getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
            AttributeInstance attribute2 = context.getLivingTarget().getAttributes().getInstance(Attributes.ATTACK_SPEED);
            if (attribute != null) {
                attribute.setBaseValue(attribute.getBaseValue() * 0.5F);
            }
            if (attribute2 != null) {
                attribute2.setBaseValue(attribute2.getBaseValue() * 0.2F);
            }
        }
    }


    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if (event.phase == TickEvent.Phase.START && !event.player.level().isClientSide() ) {
//            ServerLevel level = (ServerLevel) event.player.level();
//            BlockPos playerPos = event.player.blockPosition();
//            if (ModifierLevel.getTotalArmorModifierlevel(event.player, Modifiers.made_in_heaven.getId()) > 0) {
//                for (int i = 0; i < MULTIPLIER; i++) {
//                    performExtraRandomTicks(level, playerPos);
//                }
//            }
//        }
        ModDataNBT entitydata = ModDataNBT.readFromNBT(event.player.getPersistentData());
    }

    private static void performExtraRandomTicks(ServerLevel level, BlockPos center) {
        for (int i = 0; i < 100; i++) {
            BlockPos randomPos = getRandomPosAround(level, center, RADIUS); // 传入level参数
            BlockState state = level.getBlockState(randomPos);
            if (state.isRandomlyTicking()) {
                state.randomTick(level, randomPos, level.random);
            }
        }
    }

    private static BlockPos getRandomPosAround(ServerLevel level, BlockPos center, int radius) {
        RandomSource random = level.random;
        int x = center.getX() + random.nextInt(radius * 2) - radius;
        int y = Mth.clamp(
                center.getY() + random.nextInt(radius * 2) - radius,
                level.getMinBuildHeight(),
                level.getMaxBuildHeight() - 1
        );
        int z = center.getZ() + random.nextInt(radius * 2) - radius;
        return new BlockPos(x, y, z);
    }
}
