package com.fuyun.cloudertinker.extend.interfaces;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.*;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface AboutArmor extends DamageBlockModifierHook, OnAttackedModifierHook, ModifyDamageModifierHook, ProtectionModifierHook, ElytraFlightModifierHook, EquipmentChangeModifierHook {
    default void initarmorinterface(ModuleHookMap.@NotNull Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_BLOCK, ModifierHooks.ON_ATTACKED, ModifierHooks.MODIFY_DAMAGE);
    }

    default float modifyDamageTaken(IToolStackView armor, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage) {
        Entity var9 = source.getEntity();
        if (var9 instanceof LivingEntity enemy) {
            LivingEntity entity = context.getEntity();
            return this.TrueDamageamount(armor, modifier.getLevel(), context, slot, source, amount, isDirectDamage, entity, enemy);
        } else {
            return amount;
        }
    }

    default float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        return amount;
    }

    default boolean isDamageBlocked(IToolStackView armor, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        return this.canBlockedSource(armor, modifier.getLevel(), context, slot, source, amount);
    }

    default boolean canBlockedSource(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        return false;
    }

    default boolean elytraFlightTick(IToolStackView iToolStackView, ModifierEntry modifierEntry, LivingEntity livingEntity, int i) {
        return false;
    }

    default void onAttacked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
    }

    default float getProtectionModifier(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v) {
        return 0.0F;
    }
}
