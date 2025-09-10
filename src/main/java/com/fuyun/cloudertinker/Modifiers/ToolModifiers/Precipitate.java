package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Precipitate extends Modifier implements BreakSpeedModifierHook {
//沉积
public boolean havenolevel() {
    return true;
}
    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        event.setNewSpeed(event.getNewSpeed() + (this.getBonusPercentage(event.getEntity()) * event.getOriginalSpeed()));
    }
    private float getBonusPercentage(LivingEntity entity) {
        float maxHealth = entity.getMaxHealth();
        return (maxHealth - entity.getHealth()) / maxHealth;
    }
}
