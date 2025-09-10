package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import util.method.ModifierLevel;

public class Hydralife extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player&&player.tickCount %20 ==0) {
            if (ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
                if (player.getFoodData().getFoodLevel() > 10) {
                    if (player.getHealth() <= player.getMaxHealth() * 0.5) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 2));
                        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 2));
                    }
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
                }
            }
            }
        }
    }

