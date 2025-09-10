package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class Magent extends BattleModifier implements BreakSpeedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED);
    }
    @Override
    public void afterMeleeHit(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget()!=null) {
            TinkerModifiers.repulsiveEffect.get().apply(context.getAttacker(), 5, -7+(modifier.getLevel()-1)*4);
        }
    }
    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        Level world=event.getEntity().level;
        BlockPos pos=event.getPosition().get();
        BlockState blockState=world.getBlockState(pos);
        Block block=blockState.getBlock();
        if (blockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES) ||blockState.is(BlockTags.STONE_ORE_REPLACEABLES)){
            event.setNewSpeed(event.getNewSpeed()*1.5f);
        }
    }

}
