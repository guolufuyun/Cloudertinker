package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class NoDieNoEND extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    private static final ResourceLocation resentment = Cloudertinker.getResource("resentment");
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
     ModDataNBT tooldata = tool.getPersistentData();
        consumer.accept(Attributes.ARMOR, new AttributeModifier(UUID.fromString("318662D1-A6BF-38C8-5AB6-04B7F7BCC619"), String.valueOf(Attributes.ARMOR), (int) (tooldata.getFloat(resentment)*0.1), AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            list.add(Component.translatable("modifier.cloudertinker.nodienoend.tooltip", (int)(tooldata.getFloat(resentment)*0.1)).withStyle(ChatFormatting.DARK_RED));
        }
        }
}
