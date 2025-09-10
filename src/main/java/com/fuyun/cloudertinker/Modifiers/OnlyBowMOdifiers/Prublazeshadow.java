package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Prublazeshadow extends BattleModifier {

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {

        if (target != null) {
            MobEffectInstance instance1 =attacker.getEffect(MobEffects.MOVEMENT_SPEED);
            MobEffectInstance instance2 =target.getEffect(MobEffects.BLINDNESS);
            if  (instance1 != null) {
                int timeleft = attacker.getEffect(MobEffects.MOVEMENT_SPEED).getDuration();
                int EffectLevel = attacker.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier();
                attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, timeleft  ,  EffectLevel + 1));
                arrow.setBaseDamage(arrow.getBaseDamage() + (0.1 * EffectLevel));
            }
            if (instance2 != null) {
                int timeleft = target.getEffect(MobEffects.BLINDNESS).getDuration();
                int EffectLevel = target.getEffect(MobEffects.BLINDNESS).getAmplifier();
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, timeleft  ,  EffectLevel + 1));
                arrow.setBaseDamage(arrow.getBaseDamage() + (0.1 * EffectLevel));
            }
        }



        if (target != null) {
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100 * level  ,  level-1));
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100 * level ,  level-1));

        }
    }










}
