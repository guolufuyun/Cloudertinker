package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import slimeknights.tconstruct.shared.TinkerEffects;
import slimeknights.tconstruct.tools.TinkerModifiers;
import util.method.ModifierEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Variablood extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (livingTarget != null) {
            LivingEntity entity= livingTarget;
            int isbene=1;
            if (ModifierEffect.hasbeneficialEffect(attacker)) isbene=2;
            entity.addEffect(new MobEffectInstance(TinkerEffects.bleeding.get(), attacker.getArmorValue()/5 * 20 *isbene ,  (int)(attacker.getMaxHealth()/10)-1));
            return damage+( damage *0.4f*isbene);
        }
        return damage *1.4f;
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null&&context.getLivingTarget().isAlive()){
            context.getLivingTarget().invulnerableTime = 0;
            int isbene=1;
            if (ModifierEffect.hasbeneficialEffect(context.getAttacker())) isbene=2;
            context.getLivingTarget().hurt(context.getLivingTarget().damageSources().magic(),damageDealt * 0.2f*isbene );
            context.getLivingTarget().setLastHurtByMob(context.getAttacker());
        }
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, ModDataNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            LivingEntity entity= target;
            int isbene=1;
            if (ModifierEffect.hasbeneficialEffect(attacker)) isbene=2;
            entity.hurt(entity.damageSources().magic(), (attacker.getMaxHealth() * 0.2f*isbene));
            entity.addEffect(new MobEffectInstance(TinkerEffects.bleeding.get(), attacker.getArmorValue()/5 * 20*isbene  ,  (int)(attacker.getMaxHealth()/10)-1));
            entity.invulnerableTime = 0;

        }
    }
    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT namespacedNBT, boolean primary) {
        if (arrow != null) {
            int isbene=1;
            if (ModifierEffect.hasbeneficialEffect(shooter)) isbene=2;
            arrow.setBaseDamage(arrow.getBaseDamage()+(arrow.getBaseDamage() * 0.4*isbene));
        }
    }
}
