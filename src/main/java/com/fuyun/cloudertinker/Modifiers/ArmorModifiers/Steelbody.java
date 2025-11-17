package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.BiConsumer;

public class Steelbody extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    static  final Attribute[] armorAttribute = new Attribute[]{
            Attributes.ATTACK_DAMAGE,
            Attributes.ARMOR,
            Attributes.ARMOR_TOUGHNESS,
            Attributes.ATTACK_SPEED,
    } ;
    public UUID UUIDFromWeapon(EquipmentSlot solt, ModifierId modifierId){
        return UUID.nameUUIDFromBytes((solt.getName()+modifierId.toString()).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (tool.hasTag(TinkerTags.Items.ARMOR)){

            consumer.accept(Attributes.ARMOR, new AttributeModifier(UUIDFromWeapon(slot,this.getId()), String.valueOf(Attributes.ARMOR), 4, AttributeModifier.Operation.ADDITION));
            consumer.accept(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUIDFromWeapon(slot,this.getId()), String.valueOf(Attributes.ARMOR_TOUGHNESS), 2, AttributeModifier.Operation.ADDITION));


        }
    }
}
