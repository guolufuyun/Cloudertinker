package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import util.method.ModifierLevel;

public class Druidseed extends BattleModifier {

    public boolean havenolevel() {
        return true;
    }

    @Override
    public void LivingDamageEvent(LivingDamageEvent event) {
        super.LivingDamageEvent(event);
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            Entity entity1=event.getSource().getEntity();
            if (entity1 instanceof LivingEntity livingEntity){
                if (ModifierLevel.EquipHasModifierlevel(livingEntity, CloudertinkerModifiers.druidseed.getId())) {
                if ((entity instanceof TamableAnimal animal&&animal.isTame())||entity instanceof Player||entity instanceof AbstractGolem){
                    entity.heal(event.getAmount()+1);
                    event.setAmount(1);
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,140, 1));
                }else {
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.MAGIC.bypassArmor(),event.getAmount()*0.2f);
                    entity.invulnerableTime = 0;
                    entity.addEffect(new MobEffectInstance(MobEffects.POISON,200, 1));
                    entity.setLastHurtByMob(livingEntity);
                }
                }
            }else if(entity1 instanceof Projectile projectile){
                if (ModifierLevel.EquipHasModifierlevel((LivingEntity) projectile.getOwner(), CloudertinkerModifiers.druidseed.getId())) {
                    if ((entity instanceof TamableAnimal animal&&animal.isTame())||entity instanceof Player||entity instanceof AbstractGolem){
                        entity.heal(event.getAmount()+1);
                        event.setAmount(1);
                        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,100, 1));
                    }else {
                        entity.invulnerableTime = 0;
                        entity.hurt(DamageSource.MAGIC.bypassArmor(),event.getAmount()*0.2f);
                        entity.invulnerableTime = 0;
                        entity.addEffect(new MobEffectInstance(MobEffects.POISON,200, 1));
                        entity.setLastHurtByMob((LivingEntity) projectile.getOwner());
                    }
                }
            }
        }
    }
}
