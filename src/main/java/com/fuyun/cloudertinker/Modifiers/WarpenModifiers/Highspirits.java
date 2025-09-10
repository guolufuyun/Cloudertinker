package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class Highspirits extends BattleModifier {
    @Override
    public float modifyStat(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (!living.getCommandSenderWorld().isClientSide){
            if (living instanceof Player player&&player.getFoodData().getFoodLevel()>=20){
                if (stat == ToolStats.ATTACK_SPEED) {
                    return (float)(baseValue +  modifier.getLevel() * 0.1 * tool.getMultiplier(ToolStats.ATTACK_SPEED));
                } else if (stat == ToolStats.ATTACK_DAMAGE) {
                    return (float)(baseValue +  modifier.getLevel() * 0.1* tool.getMultiplier(ToolStats.ATTACK_DAMAGE));
                }
            }
            }
        return baseValue;
    }

}
