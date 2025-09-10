package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.List;

public class Growhedge extends ArmorModifier implements RequirementsModifierHook, ValidateModifierHook {
    public boolean havenolevel() {
        return true;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.REQUIREMENTS,ModifierHooks.VALIDATE);
    }
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy != null&&enemy!=entity&&source instanceof EntityDamageSource entityDamageSource &&!entityDamageSource.isThorns()) {
            enemy.hurt(DamageSource.thorns(entity),amount*3);
            enemy.setLastHurtByMob(entity);
           
        }
        return amount;
    }
    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of();
    }

    @Override
    public @Nullable Component validate(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        if (iToolStackView.getModifierLevel(TinkerModifiers.thorns.getId())>=3){
            return null;
        }
        return requirementsError(modifierEntry);
    }
    @Override
    public @Nullable net.minecraft.network.chat.Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipes.modifier.cloudertinker.growhedge");
    }
}
