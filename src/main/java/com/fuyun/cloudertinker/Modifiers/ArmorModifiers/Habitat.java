package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.BiomeKeys;

public class Habitat extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player && isCorrectSlot ) {
            if (player.getLevel().getBiome(player.blockPosition()).is(BiomeKeys.ENCHANTED_FOREST)) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1));
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 40, 2));
            }
        }
    }
}
