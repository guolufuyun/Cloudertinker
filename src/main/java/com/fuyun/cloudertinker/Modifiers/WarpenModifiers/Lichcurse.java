package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
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
        if (livingTarget != null) {
            LivingEntity entity= livingTarget;
            entity.hurt(DamageSource.MAGIC,damage * 0.5f );
            entity.setLastHurtByMob(attacker);
            entity.invulnerableTime = 0;
        }
        return damage *0.5f;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            target.hurt(DamageSource.MAGIC, (attacker.getHealth() * 0.2f));
            target.invulnerableTime = 0;
            arrow.setBaseDamage(arrow.getBaseDamage());
            target.setLastHurtByMob(attacker);
        }

    }
}

