package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.BiConsumer;

import static com.fuyun.cloudertinker.Modifiers.WarpenModifiers.Twilight.Twilight_KEY;

public class Twilightarmor extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    private static final ResourceLocation KEY = new ResourceLocation(Cloudertinker.MOD_ID, "twilightarmor");
    public UUID UUIDFromWeapon(EquipmentSlot solt, ModifierId modifierId) {
        return UUID.nameUUIDFromBytes((solt.getName() + modifierId.toString()).getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (tool.hasTag(TinkerTags.Items.ARMOR)) {
            ModDataNBT persistentData=tool.getPersistentData();
            if (persistentData.getBoolean(KEY)) consumer.accept(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUIDFromWeapon(slot, this.getId()), String.valueOf(Attributes.ARMOR_TOUGHNESS), 1, AttributeModifier.Operation.ADDITION));
            else consumer.accept(Attributes.ARMOR, new AttributeModifier(UUIDFromWeapon(slot, this.getId()), String.valueOf(Attributes.ARMOR), 1, AttributeModifier.Operation.ADDITION));
        }
    }
    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(KEY);
        return null;}
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ModDataNBT persistentData=tool.getPersistentData();
        persistentData.putBoolean(KEY,world.dimension().equals(Twilight_KEY));
    }
}