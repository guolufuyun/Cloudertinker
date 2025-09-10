package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.TwilightItemTier;

public class Harmonize extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    private static int averageInt(float value) {
        double floor = Math.floor(value);
        double rem = value - floor;
        return (int) floor + (Math.random() < rem ? 1 : 0);
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
       if (attacker instanceof Player player &&target!=null){
           int extra=1;
          steelleafdamage(target,player,extra);
           target.setLastHurtByMob(attacker);
       }

    }


    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage ) {
        if (context.getLivingTarget()!=null&&context.getAttacker() instanceof Player player){
            int extra=1;
            if (context.isExtraAttack()) extra =4;
            steelleafdamage(context.getLivingTarget(),player,extra);
            context.getLivingTarget().setLastHurtByMob(context.getAttacker());
        }
    }

    public void steelleafdamage(LivingEntity entity, Player player,int extra){
        int healPower = 0;
        NonNullList<ItemStack> playerInv = player.getInventory().items;
        for (int i = 0; i < 9; i++) {
            ItemStack invSlot = playerInv.get(i);
            if (invSlot.is(TFItems.STEELEAF_INGOT.get())) {
                healPower += invSlot.getCount();
            } else if (invSlot.is(TFBlocks.STEELEAF_BLOCK.get().asItem())) {
                healPower += invSlot.getCount() * 9;
            } else if (TFItemStackUtils.hasToolMaterial(invSlot, TwilightItemTier.STEELEAF)) {
                healPower += 1;
            }
        }
        entity.invulnerableTime = 0;
        if ((float) healPower/9<=64){
            entity.hurt(DamageSource.MAGIC.bypassArmor(),averageInt((float) healPower /(9*extra)));

        }else {
            entity.hurt(DamageSource.MAGIC.bypassArmor(), (float) 64 /extra);

        }
    }
}
