package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFMobEffects;

public class Chill_aura extends ArmorModifier {
    public static boolean enabled = ModList.get().isLoaded("twilightforest");
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enabled && enemy  != null&&enemy!=entity&&source instanceof EntityDamageSource entityDamageSource &&!entityDamageSource.isThorns()) {
            MobEffectInstance instance =enemy.getEffect(TFMobEffects.FROSTY.get());
            if (instance!=null){
                enemy.hurt(DamageSource.FREEZE.bypassArmor(), (float) (amount * 0.05 *level));
                enemy.invulnerableTime = 0;
                enemy.setLastHurtByMob(entity);
            }
            enemy.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(),20 * level, level));
        }
        return amount;
    }
}
