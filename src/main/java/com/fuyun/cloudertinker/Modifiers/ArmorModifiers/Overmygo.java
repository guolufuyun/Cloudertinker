package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

public class Overmygo extends ArmorModifier {
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy  != null) {
            MobEffectInstance instance =enemy.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            int current = overslime.getShield(armor);
            if (instance!=null){
                int EffectLevel = enemy.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
                return amount - (amount * 0.1f *(EffectLevel+1));
            }
        }
        return amount;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player && isCorrectSlot ) {
            if (player.tickCount % 20 == 0) {
                MobEffectInstance instance = player.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
            if (instance!=null){
                int timeleft = player.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration();
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, modifier.getLevel()-1,timeleft));
            }
            }
        }
    }
}


