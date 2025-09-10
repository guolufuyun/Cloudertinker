package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Resentment.resentment;

public class Hatred extends ArmorModifier {


    public boolean havenolevel() {
        return true;
    }

    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(resentment);
        return null;
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy != null && armor.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            if (entitydata.getInt(resentment) < 100) {
                entitydata.putInt(resentment, entitydata.getInt(resentment) + (int) amount);
                if (entitydata.getInt(resentment)>=100) entitydata.putInt(resentment, 100);
            }
        }
        return amount;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            ModDataNBT tooldata = tool.getPersistentData();
            if (player.tickCount % 20 == 0 && entitydata.getInt(resentment) > 0 && tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
                if (isSelected || isCorrectSlot) {
                    entitydata.putInt(resentment, entitydata.getInt(resentment) - 1);
                    tooldata.putInt(resentment, entitydata.getInt(resentment));
                }
            }
            if (player.tickCount % 20 == 0 && entitydata.getInt(resentment) <= 0 && tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
                if (isSelected || isCorrectSlot) {
                    entitydata.putInt(resentment, 0);
                    tooldata.putInt(resentment, entitydata.getInt(resentment));
                }
            }
            if ( entitydata.getInt(resentment) >100 && tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
                if (isSelected || isCorrectSlot) {
                    entitydata.putInt(resentment, 100);
                    tooldata.putInt(resentment, entitydata.getInt(resentment));
                }
            }
        }
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        if (tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) >= 1) return null;
        return Component.translatable(this.getDisplayName().getString() + "  " );
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            if (tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
                ModDataNBT tooldata = tool.getPersistentData();
                list.add(Component.translatable("modifier.cloudertinker.harted.tooltip", tooldata.getInt(resentment)).withStyle(ChatFormatting.DARK_RED)); }
        }
    }
}