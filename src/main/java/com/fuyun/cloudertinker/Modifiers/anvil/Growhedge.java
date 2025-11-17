package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.modifiers.effect.BleedingEffect;
import util.method.ModifierLevel;

import java.util.List;

public class Growhedge extends Modifier implements RequirementsModifierHook, ValidateModifierHook {
    public boolean havenolevel() {
        return true;
    }
    public Growhedge() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.REQUIREMENTS,ModifierHooks.VALIDATE);
    }
    public void LivingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker=entity.getLastHurtByMob();
        if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.growhedge.getId())&&attacker != null&&attacker!=entity&&!event.getSource().is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)) {
            if (attacker.isAlive()&&attacker instanceof  LivingEntity living){
                living.hurt(living.damageSources().thorns(entity),event.getAmount()*3);
                living.setLastHurtByMob(entity);
            }
        }
    }
    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of();
    }

    @Override
    public @Nullable Component validate(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        if (iToolStackView.getModifierLevel(ModifierIds.thorns)>=3){
            return null;
        }
        return requirementsError(modifierEntry);
    }
    @Override
    public @Nullable Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipes.modifier.cloudertinker.growhedge");
    }
}
