package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class Phantom extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy !=null) {
            MobEffectInstance instance =entity.getEffect(MobEffects.INVISIBILITY);
            Random random = new Random();
            int randomNum = random.nextInt(100) + 1;
            if (instance != null) {
                int timeleft = entity.getEffect(MobEffects.INVISIBILITY).getDuration();
                int EffectLevel = entity.getEffect(MobEffects.INVISIBILITY).getAmplifier();
                if (randomNum<=50){
                    entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, (int) (timeleft * 0.7),  EffectLevel +1));
                    return (float) (amount*0.5);
                }
            } else {
                    if (randomNum<=25) {
                    return (float) (amount*0.75);
                    }
                }
        }
        return amount;
    }
}
