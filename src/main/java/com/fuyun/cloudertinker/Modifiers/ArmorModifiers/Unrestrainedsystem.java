package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import util.method.ModifierLevel;

public class Unrestrainedsystem extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.unrestrainedsystem.getId())){
                if (!(event.getEffectInstance().getEffect() instanceof NoMilkEffect)){
                     event.setResult(Event.Result.DENY);
            }}
        }
    }
}
