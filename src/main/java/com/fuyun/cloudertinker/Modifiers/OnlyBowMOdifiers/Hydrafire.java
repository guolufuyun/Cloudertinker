package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.List;

public class Hydrafire extends BattleModifier {

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            double d0 = (float)arrow.getDeltaMovement().length();
            int damage = Mth.ceil(Mth.clamp(d0 * arrow.getBaseDamage(),0.0D, Float.MAX_VALUE));
            double x = target.getX();
            double y = target.getY();
            double z = target.getZ();
            if (attacker instanceof ServerPlayer player){
                int hunger = player.getFoodData().getFoodLevel();
                float full = player.getFoodData().getSaturationLevel();
                if (hunger<10) {
                    List<Mob> mobList = target.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + 1, y + 1, z + 1, x - 1, y - 1, z - 1));
                    for (Mob mob : mobList) {
                        if (mob != null) {
                            mob.invulnerableTime= 0;
                            mob.hurt(DamageSource.explosion(attacker),(float) (damage*0.05*level));
                            mob.setRemainingFireTicks((10 * hunger*level));
                            mob.invulnerableTime= 0;
                        }}
                    target.playSound(SoundEvents.GENERIC_EXPLODE,1,1);
                    if (target.getCommandSenderWorld() instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION,target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),1 ,0,0,0,0);
                    }
                } else if (hunger<=20){
                     arrow.setBaseDamage(arrow.getBaseDamage() * (hunger * 0.1));
                    List<Mob> mobList = target.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + (hunger * 0.1)*level, y + (hunger * 0.1)*level, z +(hunger * 0.1)*level, x - (hunger * 0.1)*level, y - (hunger * 0.1)*level, z - (hunger * 0.1)*level));
                    for (Mob mob : mobList) {
                        if (mob != null) {
                            mob.invulnerableTime= 0;
                            mob.hurt(DamageSource.explosion(attacker),(float) (damage*(hunger * 0.05)*level));
                            mob.setRemainingFireTicks((10 * hunger*level));
                            mob.invulnerableTime= 0;
                        }}
                    target.playSound(SoundEvents.GENERIC_EXPLODE,1+(int)(hunger * 0.1)*level,1);
                    if (target.getCommandSenderWorld() instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION,target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),1 +(int)(hunger * 0.1)*level,0,0,0,0);
                    }
                    if ( full <=0){player.getFoodData().setFoodLevel(hunger - 2);}
                    if ( full-2 >=0){player.getFoodData().setSaturation(full - 2);}else {player.getFoodData().setSaturation(0);}
                   } else  if ( full >0){
                    arrow.setBaseDamage(arrow.getBaseDamage() * 2);
                    List<Mob> mobList = target.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + 2*level, y + 2*level, z +2*level, x - 2*level, y - 2*level, z - 2*level));
                    for (Mob mob : mobList) {
                        if (mob != null) {
                            mob.invulnerableTime= 0;
                            mob.hurt(DamageSource.explosion(attacker),(float) (damage*(hunger * 0.05)*level));
                            mob.setRemainingFireTicks((10 * hunger*level));
                            mob.invulnerableTime= 0;
                        }}
                    target.playSound(SoundEvents.GENERIC_EXPLODE,1+2*level,1);
                    if (target.getCommandSenderWorld() instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION,target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),1+2*level,0,0,0,0);
                    }
                             if (full-5>0){player.getFoodData().setSaturation(full - 5);}else {player.getFoodData().setSaturation(0);}

                        }
                }
            target.setLastHurtByMob(attacker);
        }
    }
   }

