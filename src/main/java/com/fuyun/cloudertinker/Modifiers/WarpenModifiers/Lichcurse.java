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
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class Lichcurse extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (context.getLivingTarget() != null){
            context.getLivingTarget().invulnerableTime = 0;
            context.getLivingTarget().hurt(context.getLivingTarget().damageSources().magic(),1f);
            context.getLivingTarget().setLastHurtByMob(context.getAttacker());
            context.getLivingTarget().invulnerableTime = 0;
        }
        return damage *0.5f;
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null){
            context.getLivingTarget().invulnerableTime = 0;
            context.getLivingTarget().hurt(context.getLivingTarget().damageSources().indirectMagic(context.getAttacker(),context.getTarget()),damageDealt);
            context.getLivingTarget().setLastHurtByMob(context.getAttacker());
        }

    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, ModDataNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            target.hurt(target.damageSources().indirectMagic(arrow,attacker ), (attacker.getHealth() * 0.2f));
            target.invulnerableTime = 0;
            arrow.setBaseDamage(arrow.getBaseDamage());
            target.setLastHurtByMob(attacker);
        }

    }
}

