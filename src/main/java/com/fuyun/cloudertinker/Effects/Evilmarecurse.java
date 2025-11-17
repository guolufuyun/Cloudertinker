package com.fuyun.cloudertinker.Effects;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import twilightforest.init.TFDamageTypes;

import java.util.Objects;

public class Evilmarecurse extends StaticEffect{
    protected Evilmarecurse(MobEffectCategory type, int color) {
        super(type, color);
    }
    public Evilmarecurse() {
        super(MobEffectCategory.HARMFUL, 0x620591);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if(living.tickCount%20==0){
            Objects.requireNonNull(living).hurt(living.damageSources().wither(), 1 * amplifier+1);
            living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20,   1 * amplifier+1 -1 ));
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1 * amplifier+1 -1));
            living.invulnerableTime = 0;


        }
    }

}

