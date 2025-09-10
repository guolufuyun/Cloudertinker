package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Knight_honorable extends ArmorModifier implements MeleeDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy != null) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 ,  level));
        }
        return amount;
    }

    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (toolAttackContext.isFullyCharged() && toolAttackContext.getLivingTarget() != null) {
            LivingEntity entity = toolAttackContext.getAttacker();
            int level = modifierEntry.getLevel();
            if (level <= 3){
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10  ,  level-1));
                     } else {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10  ,  2));
                entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100  ,  level-3));
            }
                 }
        return v1;
    }
}



