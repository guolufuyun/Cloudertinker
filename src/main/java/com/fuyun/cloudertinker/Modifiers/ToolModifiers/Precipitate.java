package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import com.fuyun.cloudertinker.register.CloudertinkerBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.data.ForgeBiomeTagsProvider;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.block.GiantBlock;
import twilightforest.init.TFBlocks;

public class Precipitate extends Modifier implements BreakSpeedModifierHook {

public boolean havenolevel() {
    return true;
}

    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        event.setNewSpeed(event.getNewSpeed() + (this.getBonusPercentage(event.getEntity()) * event.getOriginalSpeed()));

    }
    private float getBonusPercentage(LivingEntity entity) {
        float maxHealth = entity.getMaxHealth();
        return (maxHealth - entity.getHealth()) / maxHealth;
    }

}
