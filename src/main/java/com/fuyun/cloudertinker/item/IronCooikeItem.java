package com.fuyun.cloudertinker.item;

import com.fuyun.cloudertinker.register.CloudertinkerItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IronCooikeItem extends Item {
    public IronCooikeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack item, Player p_41399_, LivingEntity entity, InteractionHand p_41401_) {
        if (entity instanceof IronGolem ironGolem &&item.getItem()== CloudertinkerItem.iron_cookie.get()&&ironGolem.getHealth()!=ironGolem.getMaxHealth()){
            entity.heal(10);
            entity.playSound(SoundEvents.IRON_GOLEM_REPAIR,1,1);
            item.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
