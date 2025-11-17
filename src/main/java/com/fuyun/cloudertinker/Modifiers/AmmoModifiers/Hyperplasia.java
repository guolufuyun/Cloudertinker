package com.fuyun.cloudertinker.Modifiers.AmmoModifiers;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.TwilightItemTier;

public class Hyperplasia extends NoLevelsModifier implements InventoryTickModifierHook {
    private static final float REPAIR_DAMPENER = 1f / 3600f;
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,ModifierHooks.INVENTORY_TICK);
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player && !(entity instanceof FakePlayer)) {
            if (player.isUsingItem()||player.isBlocking()||player.gameMode.isDestroyingBlock)return;
            int healPower = 0;
            if (stack.getCount()>=stack.getMaxStackSize())return;
            NonNullList<ItemStack> playerInv = player.getInventory().items;
            if (index>=9)return;
            for (int i = 0; i < 9; i++) {
                ItemStack invSlot = playerInv.get(i);
                if (invSlot.is(TFItems.STEELEAF_INGOT.get())) {
                    healPower += invSlot.getCount();
                } else if (invSlot.is(TFBlocks.STEELEAF_BLOCK.get().asItem())) {
                    healPower += invSlot.getCount() * 9;
                } else if (TFItemStackUtils.hasToolMaterial(invSlot, TwilightItemTier.STEELEAF)) {
                    healPower += 1;
                }
            }
            int increaseAmount = averageInt(healPower * REPAIR_DAMPENER);
            stack.setCount(Math.min(stack.getCount() + increaseAmount, stack.getMaxStackSize()));
        }
    }


    private static int averageInt(float value) {
        double floor = Math.floor(value);
        double rem = value - floor;
        return (int) floor + (Math.random() < rem ? 1 : 0);
    }
}
