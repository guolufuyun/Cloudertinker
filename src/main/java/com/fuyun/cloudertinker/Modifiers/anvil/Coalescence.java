package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Coalescence extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity.tickCount %100 ==0 && entity.getEffect(MobEffects.POISON)!=null) {
            MobEffectInstance instance =entity.getEffect(MobEffects.POISON);
          entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,instance.getDuration(),instance.getAmplifier()+2));
        }
    }
}
