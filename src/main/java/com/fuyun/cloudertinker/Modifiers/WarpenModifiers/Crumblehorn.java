package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.Random;

public class Crumblehorn extends BattleModifier {


    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt)  {
        if (context.getLivingTarget() != null) {
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            LivingEntity entity =context.getLivingTarget();
            if (randomNum <= 7) {
                switch (randomNum) {
                    case 1,2,3:
                        entity.hurt(DamageSource.sonicBoom(context.getAttacker()).bypassArmor().bypassMagic(),5 * modifier.getLevel());
                        entity.invulnerableTime = 0;
                        entity.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 100  ,  modifier.getLevel()-1));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 ,  modifier.getLevel()-1));
                break;
                    case 4:
                        entity.hurt(DamageSource.sonicBoom(context.getAttacker()).bypassArmor().bypassMagic(),5 * modifier.getLevel());
                        entity.invulnerableTime = 0;
                        entity.hurt(DamageSource.thrown(context.getAttacker(), context.getAttacker()).bypassArmor(),5 * modifier.getLevel());
                        entity.invulnerableTime = 0;
                        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100  ,  modifier.getLevel()-1));
                        entity.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 100  ,  modifier.getLevel()-1));
                        entity.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(),100, modifier.getLevel()-1));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 ,  modifier.getLevel()-1));
                        break;
                    case 5,6,7:
                        entity.hurt(DamageSource.thrown(context.getAttacker(), context.getAttacker()).bypassArmor(),5 * modifier.getLevel());
                        entity.invulnerableTime = 0;
                        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100  ,  modifier.getLevel()-1));
                        entity.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(),100, modifier.getLevel()-1));
                         break;
                }
                entity.setLastHurtByMob(context.getAttacker());
            }
            entity.setLastHurtByMob(context.getAttacker());
        }
    }
}
