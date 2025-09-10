package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fml.ModList;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;

import java.util.List;

import static com.fuyun.cloudertinker.Effects.ShildCooldown.shield_cooldown;


public class Immortalguardian extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    public static boolean enabled = ModList.get().isLoaded("twilightforest");
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        ModDataNBT entitydata = ModDataNBT.readFromNBT(entity.getPersistentData());
        if (enemy instanceof Mob mob) {
            MobEffectInstance instance =entity.getEffect(CloudertinkerEffects.ShildCooldown.get());
            if ( instance != null||entitydata.getInt(shield_cooldown)>0) {


                } else { if (enabled){entity.getCapability(CapabilityList.SHIELDS).ifPresent(IShieldCapability::replenishShields);
                entitydata.putInt(shield_cooldown, 6000);
                    entity.addEffect(new MobEffectInstance(CloudertinkerEffects.ShildCooldown.get(),6000, 0));}}
            }

        return amount;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(player.getPersistentData());
            list.add(Component.translatable("modifier.cloudertinker.shieldcccldown.tooltip", entitydata.getInt(shield_cooldown)/20).withStyle(ChatFormatting.YELLOW));
        }
    }
}





