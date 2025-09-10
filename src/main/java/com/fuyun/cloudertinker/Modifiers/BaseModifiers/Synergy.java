package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.TwilightItemTier;

public class Synergy extends BattleModifier {
//    同调
public boolean havenolevel() {
    return true;
}
    private static final float REPAIR_DAMPENER = 1f / 256f;
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player && !(entity instanceof FakePlayer)) {
            if (!isCorrectSlot) return;
            if (!needsRepair(tool)) return;
            if (player.isUsingItem()||player.isBlocking()||player.gameMode.isDestroyingBlock)return;
            int healPower = 0;
            NonNullList<ItemStack> playerInv = player.getInventory().items;
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
            ToolDamageUtil.repair(tool, averageInt(healPower * REPAIR_DAMPENER));
        }
    }

    private static boolean needsRepair(IToolStackView tool) {
        return tool.getDamage() > 0 && !tool.isBroken();
    }

    private static int averageInt(float value) {
        double floor = Math.floor(value);
        double rem = value - floor;
        return (int) floor + (Math.random() < rem ? 1 : 0);
    }
}
