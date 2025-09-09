package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.block.GiantBlock;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlocks;

import javax.annotation.Nullable;

public class Mazebreaker extends Modifier implements BreakSpeedModifierHook, ToolDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED,ModifierHooks.TOOL_DAMAGE);
    }
    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        Level world=event.getEntity().level;
        BlockPos pos=event.getPosition().get();
        BlockState blockState=world.getBlockState(pos);
        Block block=blockState.getBlock();
        if (blockState.is(BlockTagGenerator.MAZEBREAKER_ACCELERATED) ){
            event.setNewSpeed(event.getNewSpeed()*16f);
        }
        if (block instanceof GiantBlock){
            if (block==TFBlocks.GIANT_OBSIDIAN.get())event.setNewSpeed(event.getNewSpeed()*64f);
            event.setNewSpeed(event.getNewSpeed()*64f);
        }
    }

    @Override
    public int getPriority() {
        // higher than stoneshield, overslime, and reinforced
        return 200;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return amount >= 1 ? 1 : 0;
    }
}
