/*
 * @Author: w 3519533277@qq.com
 * @Date: 2026-06-15 17:51:26
 * @LastEditors: w 3519533277@qq.com
 * @LastEditTime: 2026-06-15 17:56:27
 * @FilePath: \Cloudertinker\src\main\java\com\fuyun\cloudertinker\Effects\BlueBurnAbility.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.fuyun.cloudertinker.Effects;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import twilightforest.init.TFDamageTypes;

import java.util.Objects;

public class BlueBurn extends StaticEffect {
    public float WeaponAttack = 0f;

    protected BlueBurn(MobEffectCategory type, int color) {
        super(type, color);
    }

    public BlueBurn() {
        super(MobEffectCategory.HARMFUL, 0x620591);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living.tickCount % 20 == 0) {
            Objects.requireNonNull(living).hurt(living.damageSources().inFire(), WeaponAttack * 0.18f);

            living.invulnerableTime = 0;

        }
    }

    public float GetVal() {
        return WeaponAttack;
    }

}

