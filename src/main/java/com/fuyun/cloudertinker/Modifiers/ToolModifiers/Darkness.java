package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nonnull;
import java.util.List;

public class Darkness extends Modifier implements TooltipModifierHook, BreakSpeedModifierHook, MeleeDamageModifierHook, ConditionalStatModifierHook {
    public static int getLight(Level world, BlockPos pos) {
        return Math.max(world.getBrightness(LightLayer.SKY, pos) - world.getSkyDarken(), world.getBrightness(LightLayer.BLOCK, pos));
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.BREAK_SPEED, ModifierHooks.MELEE_DAMAGE, ModifierHooks.CONDITIONAL_STAT);
    }
    @Override
    public void onBreakSpeed(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull PlayerEvent.BreakSpeed event, @Nonnull Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        Player player = event.getEntity();
        Level world = player.getCommandSenderWorld();
        if(event.getPosition().isPresent()) {
            BlockPos pos = event.getPosition().get().above();
            int light = getLight(world, pos) + 1;
            if (light < 10) {
                event.setNewSpeed((float) (event.getNewSpeed() * (1 + 0.5 * modifier.getLevel() / light)));
            }
        }
    }

    @Override
    public float getMeleeDamage(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull ToolAttackContext context, float baseDamage, float damage) {
        Player player = context.getPlayerAttacker();
        if(player != null) {
            Level world = player.getCommandSenderWorld();
            BlockPos pos = player.getOnPos().above();
            int light = getLight(world, pos) + 1;
            if (light < 10) {
                return (float) (damage * (1 + 0.25 * modifier.getLevel() / light));
            }
        }
        return damage;
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        Level world = living.getCommandSenderWorld();
        int level = modifier.getLevel();
        BlockPos pos = living.getOnPos().above();
        int light = getLight(world, pos) + 1;
        if (light < 10) {
            if (stat == ToolStats.DRAW_SPEED) {
                return (float) (baseValue * (1 + 0.25 * level / light));
            }else if (stat == ToolStats.VELOCITY) {
                return (float)(baseValue * (1 + 0.25 * level / light));
            }
        }
        return baseValue;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (player != null) {
            if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
                if (tooltipKey == TooltipKey.SHIFT) {
                    if (harvest) {
                        TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.cloudertinker.darkness.attack_damage"), 0.25 * modifier.getLevel(), tooltip);
                        TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.cloudertinker.darkness.mining_speed"), 0.5 * modifier.getLevel(), tooltip);
                    } else {
                        TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.cloudertinker.darkness.draw_speed"), 0.25 * modifier.getLevel(), tooltip);
                        TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.cloudertinker.darkness.velocity"), 0.25 * modifier.getLevel(), tooltip);
                    }
                }
            }
        }
    }
}
