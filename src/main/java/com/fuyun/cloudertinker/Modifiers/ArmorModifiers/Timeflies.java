package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Timeflies extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player && isCorrectSlot ) {
            if (player.tickCount %20 ==0) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, 0));
                if (RANDOM.nextInt(100)+1 > 95) {
                    var item = new ItemStack[]{player.getItemBySlot(EquipmentSlot.HEAD), player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS), player.getItemBySlot(EquipmentSlot.FEET), player.getItemBySlot(EquipmentSlot.OFFHAND), player.getItemBySlot(EquipmentSlot.MAINHAND)};
                    for (ItemStack itemStack1 : item) {
                        player.getCooldowns().removeCooldown(itemStack1.getItem());
                    }
                }
            }
        }
    }
}
