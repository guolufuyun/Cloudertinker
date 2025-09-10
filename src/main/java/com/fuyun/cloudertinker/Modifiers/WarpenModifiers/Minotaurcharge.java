package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Minotaurcharge extends BattleModifier implements MeleeDamageModifierHook {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (context.getLivingTarget() != null) {
            LivingEntity entity =context.getLivingTarget();
            if (context.getAttacker() instanceof Player player) {
                if (player.isSprinting()) {
                    return damage * 1.7F;
                }
            }
        }
        return damage ;
    }
}
