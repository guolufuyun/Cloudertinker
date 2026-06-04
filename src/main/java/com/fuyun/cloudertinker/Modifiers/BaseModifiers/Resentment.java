package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

public class Resentment extends ArmorModifier {
    public static final ResourceLocation resentment = Cloudertinker.getResource("resentment");
    public Resentment() {
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
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player&& ModifierLevel.EquipHasModifierlevel(entity,this.getId())) {
            DamageSource source = event.getSource();
            if (source.getEntity() instanceof LivingEntity enemy && enemy != entity) {
                ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
                float amount = event.getAmount();

                enemy.addEffect(new MobEffectInstance(CloudertinkerEffects.ResentmentBrand.get(), 20 * entitydata.getInt(resentment), 0));

                if (entitydata.getInt(resentment) < 200) {
                    entitydata.putInt(resentment, entitydata.getInt(resentment) + (int) amount);
                    if (entitydata.getInt(resentment) >= 200) {
                        entitydata.putInt(resentment, 200);
                    }
                }
            }
        }
    }

    public void LivingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            if (player.tickCount % 20 == 0 && entitydata.getInt(resentment) > 0) {
                entitydata.putInt(resentment, entitydata.getInt(resentment) - 1);
            }
            if (entitydata.getInt(resentment) < 0) {
                entitydata.putInt(resentment, 0);
            }
            if (entitydata.getInt(resentment) > 200) {
                entitydata.putInt(resentment, 200);
            }
        }
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        return amount;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
            ModDataNBT tooldata = tool.getPersistentData();
            tooldata.putInt(resentment, entitydata.getInt(resentment));
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            list.add(Component.translatable("modifier.cloudertinker.resentment.tooltip", tooldata.getInt(resentment)).withStyle(ChatFormatting.DARK_RED));}
    }}

