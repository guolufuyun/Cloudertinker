package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.fuyun.cloudertinker.Modifiers.ToolModifiers.Darkness.getLight;

public class Darknessarmor extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
      if (isSelected||isCorrectSlot){
          BlockPos pos = entity.getOnPos().above();
          int light = getLight(world, pos) + 1;
          if(light <= 9) {
                  entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100,0));
          }
      }
    }
}
