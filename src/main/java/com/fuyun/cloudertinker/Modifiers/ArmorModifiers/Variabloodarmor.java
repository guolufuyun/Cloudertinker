package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import util.method.ModifierLevel;

import java.util.UUID;
import java.util.function.BiConsumer;

public class Variabloodarmor extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            if (ModifierLevel.getTotalArmorModifierlevel(entity, this.getId()) > 0) {
                if (entity instanceof Player player) {
                    int hunger = player.getFoodData().getFoodLevel();
                    event.setAmount(event.getAmount() - 1f);
                    if (hunger+3<=20) {
                        player.getFoodData().setFoodLevel(hunger + 3);
                    } else {
                        player.getFoodData().setFoodLevel(20);
                    }
                }
            }
        }
    }
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        consumer.accept(Attributes.ARMOR, new AttributeModifier(UUID.fromString("bd56f5db-45ed-7ced-4cf0-4f0ebf2c1717"), String.valueOf(Attributes.ARMOR), 3, AttributeModifier.Operation.ADDITION));
    }
}