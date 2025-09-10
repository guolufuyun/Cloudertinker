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

public class Winderosion extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player && isCorrectSlot ) {
            if (player.tickCount % 20 == 0&& player.getHealth()<=player.getMaxHealth()*0.75) {
                if (player.getHealth()<=player.getMaxHealth()*0.25){
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3,20));
                } else if (player.getHealth()<=player.getMaxHealth()*0.25) {
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2,20));
                }else {
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1,20));
                }
            }
        }
    }
}
