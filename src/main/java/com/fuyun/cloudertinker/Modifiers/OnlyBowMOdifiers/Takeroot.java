package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import util.mixins.LivingPositionRecord;

public class Takeroot extends BattleModifier {
    private static final ResourceLocation root = Cloudertinker.getResource("root");
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && isSelected) {
            ModDataNBT tooldata = tool.getPersistentData();
            if (holder instanceof LivingPositionRecord record) {
                if (!record.moveinLastTick(0.01d)) {
                    if (holder.tickCount % 20 == 0) {
                        if (tooldata.getInt(root) < 0) {
                            tooldata.putInt(root, 0);
                        } else if (tooldata.getInt(root) < 5) {
                            tooldata.putInt(root, tooldata.getInt(root) + 1);
                        }
                    }
                } else {
                    if (tooldata.getInt(root) > 0) {
                        tooldata.putInt(root, 0);
                    }
                }
            }
            if (tooldata.getInt(root) >= 3){
                holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,1) );
                holder.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,20,2) );
            }

    }
}
    @Override
    public float modifyStat(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
       if (!living.getCommandSenderWorld().isClientSide){
           ModDataNBT tooldata = tool.getPersistentData();
           Vec3 motion = living.getDeltaMovement();
           if (tooldata.getInt(root) >= 3) {
               if (stat == ToolStats.VELOCITY) {
                   return (float)(baseValue +  modifier.getLevel() * 0.1 * tool.getMultiplier(ToolStats.VELOCITY));
               } else if (stat == ToolStats.ACCURACY) {
                   return (float)(baseValue +  modifier.getLevel() * 0.2* tool.getMultiplier(ToolStats.ACCURACY));
               }
           }
       }
        return baseValue;
    }
}
