package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Fire_react extends ArmorModifier {
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy != null&&enemy!=entity&&source instanceof EntityDamageSource entityDamageSource &&!entityDamageSource.isThorns()) {
            if (enemy.isOnFire()){
            enemy.hurt(DamageSource.IN_FIRE.bypassArmor(), (float) (amount * 0.05 *level));
            enemy.invulnerableTime = 0;
                enemy.setLastHurtByMob(entity);
            }
            enemy.setRemainingFireTicks((20 * level));
        }
        return amount;
    }
}
