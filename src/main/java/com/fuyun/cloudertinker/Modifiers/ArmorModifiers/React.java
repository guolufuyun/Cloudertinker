package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class React extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy != null&&enemy!=entity&&source instanceof EntityDamageSource entityDamageSource &&!entityDamageSource.isThorns()&&entity instanceof Player  player) {
            if (!player.getCooldowns().isOnCooldown(armor.getItem())) {
                if (enemy.isAlive()) {
                    if (slot==EquipmentSlot.MAINHAND){
                        ToolAttackUtil.attackEntity(armor, player, InteractionHand.MAIN_HAND, enemy, () -> 1.0, true);
                    } else if (slot==EquipmentSlot.OFFHAND) {
                        ToolAttackUtil.attackEntity(armor, player, InteractionHand.OFF_HAND, enemy, () -> 1.0, true);
                    }
                    enemy.setLastHurtByMob(player);
                    player.getCooldowns().addCooldown(armor.getItem(), 15);
                }
            }
        }
        return amount;
    }
}
