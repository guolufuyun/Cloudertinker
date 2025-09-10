package com.fuyun.cloudertinker.Effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ResentmentBrand extends StaticEffect{
    protected ResentmentBrand(MobEffectCategory type, int color) {
        super(type, color);
    }
    public ResentmentBrand() {
        super(MobEffectCategory.HARMFUL, 0x620591);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if(living.tickCount%20==0){
            living.setRemainingFireTicks((100 * (amplifier+1)));
            living.invulnerableTime = 0;
        }
    }
}
