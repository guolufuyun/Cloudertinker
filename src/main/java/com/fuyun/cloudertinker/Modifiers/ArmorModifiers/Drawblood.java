package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFDamageTypes;

public class Drawblood extends ArmorModifier {
    public static boolean enabled = ModList.get().isLoaded("twilightforest");
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enabled&enemy != null&&enemy!=entity&& !source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)) {

                enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * level,2));
                enemy.hurt(enemy.damageSources().thorns(entity), (int) ((1 + amount * 0.1) * level));
                entity.heal((float) (0.5* level));
            enemy.setLastHurtByMob(entity);
        }
        return amount;
    }

}

