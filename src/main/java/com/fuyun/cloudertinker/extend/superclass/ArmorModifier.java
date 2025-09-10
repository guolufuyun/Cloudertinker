//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fuyun.cloudertinker.extend.superclass;

import com.fuyun.cloudertinker.extend.interfaces.AboutArmor;
import com.fuyun.cloudertinker.extend.interfaces.AboutBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.*;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class ArmorModifier extends Modifier implements AboutArmor, DamageBlockModifierHook, OnAttackedModifierHook, ModifyDamageModifierHook, ProtectionModifierHook, ElytraFlightModifierHook, EquipmentChangeModifierHook, AboutBuilder, VolatileDataModifierHook, GeneralInteractionModifierHook {
    public ArmorModifier() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(this::MobEffectEvent);
        MinecraftForge.EVENT_BUS.addListener(this::WhenEffectRemove);
    }

    public boolean havenolevel() {
        return false;
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
    }

    public void WhenEffectRemove(MobEffectEvent.Remove event) {
    }

    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        return super.getDisplayName(tool, entry);
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
    }

    public @NotNull Component getDisplayName(int level) {
        return this.havenolevel() ? super.getDisplayName() : super.getDisplayName(level);
    }

    public void LivingHurt(LivingHurtEvent event, LivingEntity entity, Player player) {
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
        Entity var4 = event.getSource().getEntity();
        if (var4 instanceof LivingEntity entity) {
            LivingEntity var5 = event.getEntity();
            if (var5 instanceof Player player) {
                this.LivingHurt(event, entity, player);
            }
        }

    }

    public boolean hidden() {
        return false;
    }

    public boolean shouldDisplay(boolean advanced) {
        return this.hidden() ? advanced : true;
    }

    protected void registerHooks(ModuleHookMap.Builder builder) {
        this.initarmorinterface(builder);
        builder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE, ModifierHooks.ELYTRA_FLIGHT, ModifierHooks.MODIFY_HURT, ModifierHooks.VOLATILE_DATA);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK, ModifierHooks.ON_ATTACKED, ModifierHooks.MODIFY_DAMAGE);
        builder.addHook(this, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOL_STATS, ModifierHooks.INVENTORY_TICK, ModifierHooks.TOOL_DAMAGE, ModifierHooks.TOOLTIP, ModifierHooks.GENERAL_INTERACT);
    }

    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
    }

    public @Nullable Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        return null;
    }
}
