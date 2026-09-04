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
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import twilightforest.init.TFDamageTypes;

import java.util.Objects;

public class BlueBurn extends NoMilkEffect {
    public float WeaponAttack = 0f;

    public BlueBurn() {
        super(MobEffectCategory.HARMFUL, 0XFFD700,true);
    }

    /**
     * 每 20 tick（1 秒）结算一次。
     *
     * <p>{@code MobEffect#isDurationEffectTick} 默认返回 false，不覆写它的话原版永远不会调用

     * 这正是蓝焰「挂上了却不掉血」的原因。判定写法与匠魂 {@code BleedingEffect} 保持一致。
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0 && duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        // 伤害与火焰只在服务端结算（客户端 hurt 本身会 no-op，这里显式挡掉更干净）
        if (living.level().isClientSide) {
            return;
        }
        float weaponAttack = 0f;
        MobEffectInstance instance = living.getEffect(this);
        if (instance instanceof BlueBurnInstance bInstance) {
            weaponAttack = bInstance.getWeaponAttack();
        }
        living.invulnerableTime = 0;
        living.hurt(living.damageSources().inFire(), weaponAttack * (CTKConfig.COMMON.Blue_Burn_Damage.get().floatValue()));
        living.invulnerableTime = 0;
        living.setRemainingFireTicks(living.getRemainingFireTicks() + 21);
    }


    public float GetVal() {
        return WeaponAttack;
    }

}

