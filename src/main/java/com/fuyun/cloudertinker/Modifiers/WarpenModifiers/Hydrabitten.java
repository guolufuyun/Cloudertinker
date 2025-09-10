package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Hydrabitten extends BattleModifier {

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v1) {
        if (toolAttackContext.getLivingTarget() != null) {
            LivingEntity entity = toolAttackContext.getLivingTarget();
            LivingEntity attacker = toolAttackContext.getAttacker();
            if (attacker instanceof ServerPlayer player){
                int hunger = player.getFoodData().getFoodLevel();
                if (hunger <6){
                    entity.hurt(DamageSource.playerAttack((Player) attacker),modifierEntry.getLevel() * ((20-hunger) + v1 * (8-hunger) /8));
                    entity.invulnerableTime = 0;
                    player.getFoodData().setFoodLevel(20);
                } else {
                    entity.setRemainingFireTicks((100 * modifierEntry.getLevel()));
                    entity.hurt(DamageSource.IN_FIRE.bypassArmor(), (float) (v1 * 0.15 * modifierEntry.getLevel()));
                    if (hunger + modifierEntry.getLevel() <= 20) {
                        player.getFoodData().setFoodLevel( (player.getFoodData().getFoodLevel() + modifierEntry.getLevel()));
                    } else if(player.getFoodData().getSaturationLevel() +  modifierEntry.getLevel() <= 20){
                        player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() +  modifierEntry.getLevel());
                        player.getFoodData().setFoodLevel(20);
                    } else {
                        player.getFoodData().setSaturation(20);
                    }
                }
            }
            toolAttackContext.getLivingTarget().setLastHurtByMob(toolAttackContext.getAttacker());
        }
    }
}