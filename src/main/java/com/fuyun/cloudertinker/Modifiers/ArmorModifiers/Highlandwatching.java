package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import util.mixins.LivingPositionRecord;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class Highlandwatching extends BattleModifier {
    static final ResourceLocation harder = Cloudertinker.getResource("harder");
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && (isSelected||isCorrectSlot)) {
            ModDataNBT tooldata = tool.getPersistentData();
            if (holder instanceof LivingPositionRecord record) {
                if (!record.moveinLastTick(0.01d)) {
                    if (holder.tickCount % 100 == 0) {
                        if (tooldata.getInt(harder) < 0) {
                            tooldata.putInt(harder, 0);
                        } else if (tooldata.getInt(harder) < 10) {
                            tooldata.putInt(harder, tooldata.getInt(harder) + 1);
                        }
                    }
                } else if (holder.tickCount % 10 == 0) {
                    if (tooldata.getInt(harder) > 0) {
                        tooldata.putInt(harder, tooldata.getInt(harder) - 1);
                    }
                }
            }
        }
    }

        public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer< Attribute, AttributeModifier > consumer) {

            ModDataNBT tooldata = tool.getPersistentData();
            consumer.accept(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("318662D1-A6BF-38C8-5AB6-04B7F7BCC619"), String.valueOf(Attributes.ARMOR_TOUGHNESS), (int) (tooldata.getFloat(harder)*0.5), AttributeModifier.Operation.ADDITION));
        }

        @Override
        public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List< Component > list, TooltipKey
        key, TooltipFlag tooltipFlag) {
            if (player != null) {
                ModDataNBT tooldata = tool.getPersistentData();
                list.add(Component.translatable("modifier.cloudertinker.highlandwatching.tooltip",(int)(tooldata.getFloat(harder)*0.5) ).withStyle(ChatFormatting.GRAY));
            }
        }
    }

