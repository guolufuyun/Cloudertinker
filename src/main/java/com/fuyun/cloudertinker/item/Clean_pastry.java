package com.fuyun.cloudertinker.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Clean_pastry extends Item {
    public Clean_pastry(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if ((entity instanceof Player player) ) {
            Collection<MobEffectInstance> effects=player.getActiveEffects();
            List<MobEffectInstance>effectInstances=new ArrayList<>(effects);
            for(MobEffectInstance effectlist : effectInstances){
                if (!(effectlist.getEffect() instanceof NoMilkEffect)){
                    player.removeEffect(effectlist.getEffect());
                }
            }
        }
        return result;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(net.minecraft.network.chat.Component.translatable("cloudertinker.item.tooltip.clean_pastry1").withStyle(ChatFormatting.LIGHT_PURPLE));

    }
}