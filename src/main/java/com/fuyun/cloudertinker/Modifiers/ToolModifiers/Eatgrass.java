package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.KeybindInteractModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Eatgrass extends NoLevelsModifier implements KeybindInteractModifierHook {

    private static final Random RANDOM = new Random();

    private static final Map<Block, Block> GRASS_TO_DIRT = new HashMap<>();

    static {
        GRASS_TO_DIRT.put(Blocks.GRASS_BLOCK, Blocks.DIRT);
        GRASS_TO_DIRT.put(Blocks.PODZOL, Blocks.DIRT);
        GRASS_TO_DIRT.put(Blocks.MYCELIUM, Blocks.DIRT);
        GRASS_TO_DIRT.put(Blocks.DIRT_PATH, Blocks.DIRT);
        GRASS_TO_DIRT.put(Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT);
    }



    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ARMOR_INTERACT);
    }

    @Override
    public boolean startInteract(IToolStackView tool, ModifierEntry modifier, Player player,
                                 EquipmentSlot slot, TooltipKey keyModifier) {
        if (player.level().isClientSide) {
            return false;
        }

        Level level = player.level();
        BlockPos pos = player.blockPosition().below();
        BlockState state = level.getBlockState(pos);

        Block targetDirt = GRASS_TO_DIRT.get(state.getBlock());
        if (targetDirt == null) {
            return false;
        }

        level.setBlock(pos, targetDirt.defaultBlockState(), Block.UPDATE_ALL);
        level.levelEvent(2001, pos, Block.getId(state));

        player.getFoodData().eat(1, 0.6F);

        DyeColor[] colors = DyeColor.values();
        DyeColor randomColor = colors[RANDOM.nextInt(colors.length)];
        ItemStack wool = new ItemStack(getWoolByColor(randomColor));

        ItemEntity itemEntity = new ItemEntity(level,
                player.getX(), player.getY() + 0.5, player.getZ(), wool);
        level.addFreshEntity(itemEntity);

        level.playSound(null, pos, SoundEvents.SHEEP_SHEAR,
                SoundSource.PLAYERS, 0.5F, 1.0F);

        return true;
    }

    private static Block getWoolByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> Blocks.WHITE_WOOL;
            case ORANGE -> Blocks.ORANGE_WOOL;
            case MAGENTA -> Blocks.MAGENTA_WOOL;
            case LIGHT_BLUE -> Blocks.LIGHT_BLUE_WOOL;
            case YELLOW -> Blocks.YELLOW_WOOL;
            case LIME -> Blocks.LIME_WOOL;
            case PINK -> Blocks.PINK_WOOL;
            case GRAY -> Blocks.GRAY_WOOL;
            case LIGHT_GRAY -> Blocks.LIGHT_GRAY_WOOL;
            case CYAN -> Blocks.CYAN_WOOL;
            case PURPLE -> Blocks.PURPLE_WOOL;
            case BLUE -> Blocks.BLUE_WOOL;
            case BROWN -> Blocks.BROWN_WOOL;
            case GREEN -> Blocks.GREEN_WOOL;
            case RED -> Blocks.RED_WOOL;
            case BLACK -> Blocks.BLACK_WOOL;
        };
    }
}