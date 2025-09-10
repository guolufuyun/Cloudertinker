package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.register.CloudertinkerSummonModifiers;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class Zombietoba extends NoLevelsModifier implements RequirementsModifierHook, ValidateModifierHook {
    public boolean havenolevel() {
        return true;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.REQUIREMENTS, ModifierHooks.VALIDATE);
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of();
    }

    @Override
    public @Nullable Component validate(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        if (iToolStackView.getModifierLevel(CloudertinkerSummonModifiers.fashionbody.getId())>0){
            return null;
        }
        return requirementsError(modifierEntry);
    }
    @Override
    public @Nullable net.minecraft.network.chat.Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipes.modifier.cloudertinker.zombietoba");
    }
}
