package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import util.method.ModifierLevel;

public class Ravenfeather extends BattleModifier {

    @Override
    public float modifyStat(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.DRAW_SPEED) {
            return (float)(baseValue +  modifier.getLevel() * 0.2 * tool.getMultiplier(ToolStats.DRAW_SPEED));
        } else if (stat == ToolStats.ATTACK_SPEED) {
            return (float)(baseValue +  modifier.getLevel() * 0.2 * tool.getMultiplier(ToolStats.ATTACK_SPEED));
        }
        return baseValue;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player&&player.tickCount %20 ==0) {
            if (ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
                entity.addEffect(new MobEffectInstance(MobEffects.UNLUCK,600,2*modifier.getLevel()-1) );
            }
            }
    }
}
