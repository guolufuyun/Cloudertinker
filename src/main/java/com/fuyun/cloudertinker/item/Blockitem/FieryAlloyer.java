package com.fuyun.cloudertinker.item.Blockitem;

import com.fuyun.cloudertinker.CTKConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FieryAlloyer extends BlockItem {
    public FieryAlloyer(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()){
            list.add(Component.translatable("cloudertinker.item.tooltip.fiery_alloyer", CTKConfig.COMMON.FIERY_TEMPERATURE.get()).withStyle(ChatFormatting.YELLOW));
        }else {
            list.add(Component.translatable("cloudertinker.item.tooltip.holdshift").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(stack, level, list, flag);
    }
}
