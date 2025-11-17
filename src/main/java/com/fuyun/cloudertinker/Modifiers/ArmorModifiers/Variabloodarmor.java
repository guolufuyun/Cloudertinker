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
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import util.method.ModifierLevel;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.BiConsumer;

public class Variabloodarmor extends ArmorModifier {
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            if (ModifierLevel.getTotalArmorModifierlevel(entity, this.getId()) > 0) {
                if (entity instanceof Player player) {
                    int hunger = player.getFoodData().getFoodLevel();
                    event.setAmount(event.getAmount() - (1f*ModifierLevel.getTotalArmorModifierlevel(entity, this.getId())));
                    player.getFoodData().setFoodLevel(Math.min(hunger + (3*ModifierLevel.getTotalArmorModifierlevel(entity, this.getId())), 20));
                }
            }
        }
    }
    public UUID UUIDFromWeapon(EquipmentSlot solt, ModifierId modifierId){
        return UUID.nameUUIDFromBytes((solt.getName()+modifierId.toString()).getBytes(StandardCharsets.UTF_8));
    }
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        consumer.accept(Attributes.ARMOR, new AttributeModifier(UUIDFromWeapon(slot,this.getId()), String.valueOf(Attributes.ARMOR), 3*modifier.getLevel(), AttributeModifier.Operation.ADDITION));
    }
}