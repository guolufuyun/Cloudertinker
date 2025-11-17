package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

public class Overmygo extends ArmorModifier {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,  ModifierHooks.PROTECTION);
    }
    @Override
    public float getProtectionModifier(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float modifierValue) {
        Entity entity =source.getEntity();
        MobEffectInstance instance=null;
        if (entity instanceof LivingEntity livingEntity)instance =livingEntity.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        if (entity instanceof Projectile projectile &&projectile.getOwner() instanceof LivingEntity living)instance =living.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        if (instance!=null){
            int EffectLevel = instance.getAmplifier();
            return modifierValue + (2.5f *(EffectLevel+1));
        }

        return modifierValue;
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


