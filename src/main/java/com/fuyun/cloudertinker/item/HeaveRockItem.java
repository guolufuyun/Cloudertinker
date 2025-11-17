package com.fuyun.cloudertinker.item;

import com.fuyun.cloudertinker.entities.HeaveRock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HeaveRockItem extends ArrowItem {
    public HeaveRockItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public @NotNull AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        return new HeaveRock(pLevel,pShooter);
    }
}
