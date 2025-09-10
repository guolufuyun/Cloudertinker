package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import util.method.ModifierLevel;

public class Questprotect extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
       if (entity.tickCount %20 ==0&&isCorrectSlot) {
           entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0));
           entity.removeEffect(MobEffects.BLINDNESS);
           entity.removeEffect(MobEffects.DIG_SLOWDOWN);
           entity.removeEffect(MobEffects.DARKNESS);
       }
    }
    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.questprotect.getId())) {
                if (event.getEntity().getHealth() >= event.getEntity().getMaxHealth() * 0.5) {
                    if (event.getEffectInstance().getEffect() == (MobEffects.BLINDNESS) || event.getEffectInstance().getEffect() == (MobEffects.DARKNESS)
                       ||event.getEffectInstance().getEffect() == (MobEffects.DIG_SLOWDOWN)     )
                        event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}