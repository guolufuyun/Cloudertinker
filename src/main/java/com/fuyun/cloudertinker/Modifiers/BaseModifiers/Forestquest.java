package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.BiomeKeys;
import twilightforest.world.registration.TFGenerationSettings;

public class Forestquest extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    private static boolean needsRepair(IToolStackView tool) {
        return tool.getDamage() > 0 && !tool.isBroken();
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player&&player.tickCount %60 ==0 && !(entity instanceof FakePlayer)) {
            if (!needsRepair(tool)) return;
            if (player.getLevel().getBiome(player.blockPosition()).is(BiomeKeys.ENCHANTED_FOREST)||player.level.dimension().equals(TFGenerationSettings.DIMENSION_KEY)) {
                if (player.isUsingItem()||player.isBlocking()||player.gameMode.isDestroyingBlock)return;
                ToolDamageUtil.repair(tool, 1);
            }
        }
    }
}
