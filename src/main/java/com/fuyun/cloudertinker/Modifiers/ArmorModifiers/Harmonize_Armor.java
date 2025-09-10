package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.TwilightItemTier;

public class Harmonize_Armor extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide() && entity instanceof Player player && !(entity instanceof FakePlayer)&&player.tickCount %20 ==0) {
            if (!isCorrectSlot) return;
            int healPower = 0;
            NonNullList<ItemStack> playerInv = player.getInventory().items;
            for (int i = 0; i < 9; i++) {

                    ItemStack invSlot = playerInv.get(i);
                    if (invSlot.is(TFItems.STEELEAF_INGOT.get())) {
                        healPower =healPower+ invSlot.getCount();
                    } else if (invSlot.is(TFBlocks.STEELEAF_BLOCK.get().asItem())) {
                        healPower = healPower+invSlot.getCount() * 9;
                    } else if (TFItemStackUtils.hasToolMaterial(invSlot, TwilightItemTier.STEELEAF)) {
                        healPower = healPower+1;

                }
            }
            switch (healPower/10){
                case 7:entity.addEffect(new MobEffectInstance(MobEffects.LUCK,200, 0));
                case 6:entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200, 0));
                case 5:entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,200, 0));
                case 4:entity.addEffect(new MobEffectInstance(MobEffects.JUMP,200, 0));
                case 3:entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200, 0)) ;
                case 2:entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,200, 0));
                case 1:entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400, 0));
                case 0:break;
                default: entity.addEffect(new MobEffectInstance(MobEffects.LUCK,200, (healPower/10)-7));
                    entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200, 0));
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,200, 0));
                    entity.addEffect(new MobEffectInstance(MobEffects.JUMP,200, 0));
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200, 0)) ;
                    entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,200, 0));
                    entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400, 0));
            }

        }
    }
}
