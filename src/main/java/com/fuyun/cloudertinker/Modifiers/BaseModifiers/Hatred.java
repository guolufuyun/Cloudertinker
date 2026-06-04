package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import util.method.ModifierLevel;

import java.util.List;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Resentment.resentment;

public class Hatred extends ArmorModifier {
    public Hatred() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingTickEvent);
    }

    public boolean havenolevel() {
        return true;
    }

    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(resentment);
        return null;
    }

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity= event.getEntity();
        LivingEntity enemy=null;
        if(event.getSource().getEntity() instanceof LivingEntity entity1) enemy =entity1;
        else if(event.getSource().getEntity() instanceof Projectile pr &&pr.getOwner() instanceof LivingEntity entity1)
            enemy =entity1;
        if (enemy != null && ModifierLevel.EquipHasModifierlevel(entity,this.getId())&& !ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.resentment.getId())) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            if (entitydata.getInt(resentment) < 100) {
                entitydata.putInt(resentment, entitydata.getInt(resentment) + (int) event.getAmount());
                if (entitydata.getInt(resentment)>=100) entitydata.putInt(resentment, 100);
            }
        }

    }

    public void LivingTickEvent(LivingEvent.LivingTickEvent event){
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player&& !ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.resentment.getId())) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            if (player.tickCount % 20 == 0 && entitydata.getInt(resentment) > 0) {
                entitydata.putInt(resentment, entitydata.getInt(resentment) - 1);
            }
            if (entitydata.getInt(resentment) < 0) {
                entitydata.putInt(resentment, 0);
            }
            if (entitydata.getInt(resentment) > 100) {
                entitydata.putInt(resentment, 100);
            }
        }
    }


    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            ModDataNBT tooldata = tool.getPersistentData();
            if (tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
                    tooldata.putInt(resentment, entitydata.getInt(resentment));
            }
        }
    }
    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @org.jetbrains.annotations.Nullable RegistryAccess access) {
        if (tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) >= 1) return null;
        return Component.translatable(this.getDisplayName().getString() + "  " );
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            if (tool.getModifierLevel(CloudertinkerModifiers.resentment.getId()) < 1) {
                ModDataNBT tooldata = tool.getPersistentData();
                list.add(Component.translatable("modifier.cloudertinker.hatred.tooltip", tooldata.getInt(resentment)).withStyle(ChatFormatting.DARK_RED)); }

        }
    }
}