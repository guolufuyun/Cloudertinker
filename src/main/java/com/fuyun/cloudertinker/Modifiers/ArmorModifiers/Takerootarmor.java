package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import util.mixins.LivingPositionRecord;

public class Takerootarmor extends ArmorModifier {
    private static final ResourceLocation rootgrow = Cloudertinker.getResource("rootgrow");
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.getEffect(MobEffects.REGENERATION) == null) {

            ModDataNBT tooldata = tool.getPersistentData();
            if (holder instanceof LivingPositionRecord record) {
                if (!record.moveinLastTick(0.01d)) {
                    if (holder.tickCount % 20 == 0) {
                        if (tooldata.getInt(rootgrow) < 0) {
                            tooldata.putInt(rootgrow, 0);
                        } else if (tooldata.getInt(rootgrow) < 5) {
                            tooldata.putInt(rootgrow, tooldata.getInt(rootgrow) + 1);
                        }
                    }
                } else {
                    if (tooldata.getInt(rootgrow) > 0) {
                        tooldata.putInt(rootgrow, 0);
                    }
                }
            }
                if (holder.tickCount % 20 == 0 && holder.getMaxHealth() > holder.getHealth() && (isSelected || isCorrectSlot)) {
                    if (tooldata.getInt(rootgrow) >= 3) {
                        holder.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, 1));
                    } else {
                        holder.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, 0));
                        ToolDamageUtil.damageAnimated(tool, 1, holder);
                    }
                }
            }
        }
    }

