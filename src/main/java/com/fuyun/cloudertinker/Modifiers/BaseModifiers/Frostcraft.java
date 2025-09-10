package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

public class Frostcraft extends BattleModifier {
    public static final ResourceLocation frostcraft = Cloudertinker.getResource("frostcraft");

    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(frostcraft);
        return null;}

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = modifier.getLevel();
            if (player.tickCount %20 ==0 &&tooldata.getInt(frostcraft)<level * 30 + 30 && !isSelected && !isCorrectSlot){
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)+1);

            }
            if (player.tickCount %20 ==0 &&tooldata.getInt(frostcraft)<0){tooldata.putInt(frostcraft,0);}
        }
    }




    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            list.add(Component.translatable("modifier.cloudertinker.frostcraft.tooltip", tooldata.getInt(frostcraft)).withStyle(ChatFormatting.AQUA));
        }
    }
}
