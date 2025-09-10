package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import util.method.ModifierLevel;

public class Lichprotect extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.lichprotect.getId())){
            if (event.getEntity().getHealth() >= event.getEntity().getMaxHealth() * 0.5) {
                if (event.getEffectInstance().getEffect() == (MobEffects.WEAKNESS) || event.getEffectInstance().getEffect() == (MobEffects.MOVEMENT_SLOWDOWN)
                        || event.getEffectInstance().getEffect() == (MobEffects.POISON) || event.getEffectInstance().getEffect() == (MobEffects.WITHER))
                    event.setResult(Event.Result.DENY);
            }
        }
    }
}}