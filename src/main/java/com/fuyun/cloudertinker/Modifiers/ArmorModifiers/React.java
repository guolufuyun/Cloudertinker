package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class React extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enemy != null && enemy != entity && !source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && entity instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(armor.getItem())) {
                if (enemy.isAlive()) {
                    if (slot == EquipmentSlot.MAINHAND) {
                        // 使用新的API构建攻击上下文
                        ToolAttackContext attackContext = ToolAttackContext.attacker(player)
                                .target(enemy)
                                .slot(EquipmentSlot.MAINHAND, InteractionHand.MAIN_HAND)
                                .cooldown(1.0f)
                                .toolAttributes(armor)
                                .extraAttack()
                                .build();
                        ToolAttackUtil.performAttack(armor, attackContext);
                    } else if (slot == EquipmentSlot.OFFHAND) {
                        // 使用新的API构建攻击上下文
                        ToolAttackContext attackContext = ToolAttackContext.attacker(player)
                                .target(enemy)
                                .slot(EquipmentSlot.OFFHAND, InteractionHand.OFF_HAND)
                                .cooldown(1.0f)
                                .toolAttributes(armor)
                                .extraAttack()
                                .build();
                        ToolAttackUtil.performAttack(armor, attackContext);
                    }
                    enemy.setLastHurtByMob(player);
                    player.getCooldowns().addCooldown(armor.getItem(), 15);
                }
            }
        }
        return amount;
    }

}
