package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class Variablood extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (livingTarget != null) {
            LivingEntity entity= livingTarget;

            entity.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(), attacker.getArmorValue()/5 * 20  ,  (int)(attacker.getMaxHealth()/10)-1));

            return damage *1.4f;
        }
        return damage *1.4f;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null&&context.getLivingTarget().isAlive()){
            context.getLivingTarget().invulnerableTime = 0;
            context.getLivingTarget().hurt(DamageSource.MAGIC,damageDealt * 0.2f );
            context.getLivingTarget().setLastHurtByMob(context.getAttacker());
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            LivingEntity entity= target;
            entity.hurt(DamageSource.MAGIC,  (attacker.getMaxHealth() * 0.2f));
            entity.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(), attacker.getArmorValue()/5 * 20  ,  (int)(attacker.getMaxHealth()/10)-1));
            entity.invulnerableTime = 0;

        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (arrow != null) {
            arrow.setBaseDamage(arrow.getBaseDamage() * 1.4);
        }
    }
}
