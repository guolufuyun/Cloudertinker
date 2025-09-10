package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Differencebetween extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            if (target instanceof Monster monster){
            monster.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 100 , 1 ));
            arrow.setBaseDamage(arrow.getBaseDamage() + (0.25 * arrow.getBaseDamage()));
        }
            if (target instanceof Animal animal) {
            animal.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100 , 2 ));
            arrow.setBaseDamage(0.25 * arrow.getBaseDamage());
            }
        }
    }

    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (toolAttackContext.getLivingTarget() != null) {
            LivingEntity target =toolAttackContext.getLivingTarget();
            if (target instanceof Monster monster){
                monster.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 100 , 1 ));
                return v1 * 1.25F;
            }
            if (target instanceof Animal animal) {
                animal.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100 , 2 ));
                return v1 * 0.25F;
            }
        }
        return v1;
    }

}
