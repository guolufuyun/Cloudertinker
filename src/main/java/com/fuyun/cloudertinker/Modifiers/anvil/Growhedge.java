package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
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
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
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
        if (attacker != null&&attacker!=entity&&event.getSource() instanceof EntityDamageSource entityDamageSource &&!entityDamageSource.isThorns()&&ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.growhedge.getId())) {
           if (attacker.isAlive()&&attacker instanceof  LivingEntity living){
               living.hurt(DamageSource.thorns(entity),event.getAmount()*3);
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
