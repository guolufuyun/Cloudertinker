package com.fuyun.cloudertinker.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.shared.item.CheeseItem;

import java.util.List;

public class BottleFoodItem extends Item {

    public BottleFoodItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        CheeseItem.removeRandomEffect(entity);
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!(entity instanceof Player player) || !player.getAbilities().instabuild) {
            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            if (result.isEmpty()) {
                return bottle;
            } else if (entity instanceof Player player){
                if (!player.addItem(bottle)) {
                    player.drop(bottle, false);
                }
            } else{
                entity.spawnAtLocation(bottle);
            }

        }
        return result;
    }
}
