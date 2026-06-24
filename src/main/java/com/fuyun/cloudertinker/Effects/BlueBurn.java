/*
 * @Author: w 3519533277@qq.com
 * @Date: 2026-06-15 17:51:26
 * @LastEditors: w 3519533277@qq.com
 * @LastEditTime: 2026-06-15 17:56:27
 * @FilePath: \Cloudertinker\src\main\java\com\fuyun\cloudertinker\Effects\BlueBurnAbility.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.fuyun.cloudertinker.Effects;

import com.fuyun.cloudertinker.CTKConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

import java.util.Objects;

public class BlueBurn extends NoMilkEffect {
    public float WeaponAttack = 0f;

    public BlueBurn() {
        super(MobEffectCategory.HARMFUL, 0XFFD700,true);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living.tickCount % 20 == 0) {
            Objects.requireNonNull(living).hurt(DamageSource.IN_FIRE, WeaponAttack * (CTKConfig.COMMON.Blue_Burn_Damage.get().floatValue()));

            living.invulnerableTime = 0;
            living.setRemainingFireTicks(living.getRemainingFireTicks()+21);

        }
    }

    public float GetVal() {
        return WeaponAttack;
    }

}

