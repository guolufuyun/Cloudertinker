package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Lichcurse extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
       if (context.getLivingTarget() != null){
           context.getLivingTarget().invulnerableTime = 0;
           context.getLivingTarget().hurt(DamageSource.MAGIC,1f);
           context.getLivingTarget().invulnerableTime = 0;
           context.getLivingTarget().setLastHurtByMob(context.getAttacker());
       }
        return damage *0.5f;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null){
            context.getLivingTarget().invulnerableTime = 0;
            context.getLivingTarget().hurt(DamageSource.indirectMagic(context.getAttacker(),context.getAttacker()),damageDealt);
            context.getLivingTarget().setLastHurtByMob(context.getAttacker());
        }

    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            target.hurt(DamageSource.indirectMagic(arrow,attacker ), (attacker.getHealth() * 0.2f));
            target.invulnerableTime = 0;
            arrow.setBaseDamage(arrow.getBaseDamage());
            target.setLastHurtByMob(attacker);
        }

    }
}

