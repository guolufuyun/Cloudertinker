package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
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
            entity.hurt(DamageSource.MAGIC,damage * 0.2f );
            entity.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(), attacker.getArmorValue()/5 * 20  ,  (int)(attacker.getMaxHealth()/10)-1));
            entity.invulnerableTime = 0;
            return damage *1.4f;
        }
        return damage *1.4f;
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            LivingEntity entity= target;
            entity.hurt(DamageSource.MAGIC,  (attacker.getMaxHealth() * 0.2f));
            entity.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(), attacker.getArmorValue()/5 * 20  ,  (int)(attacker.getMaxHealth()/10)-1));
            entity.invulnerableTime = 0;
            arrow.setBaseDamage(arrow.getBaseDamage() * 1.4);
        }
    }

}
