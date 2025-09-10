package com.fuyun.cloudertinker.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BottleFoodItem extends Item {

    public BottleFoodItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
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
