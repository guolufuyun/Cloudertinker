package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import util.method.ModifierLevel;

import java.util.Random;


public class Fashionbody extends BattleModifier implements  MeleeHitModifierHook, ProjectileHitModifierHook {
    public static boolean enabled = ModList.get().isLoaded("twilightforest");

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        Level world = context.getAttacker().getCommandSenderWorld();
        if (enabled && context.getLivingTarget() != null) {
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            LivingEntity entity = context.getLivingTarget();
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, modifier.getLevel()));
            if (context.isFullyCharged()&&ModifierLevel.EquipHasModifierlevel(context.getAttacker(), CloudertinkerModifiers.zombietoba.getId())){
                spawnAnimal(world, context.getLivingTarget(), context.getAttacker());
            }
            if (randomNum <= modifier.getLevel()&& !context.isExtraAttack()) {
                spawnAnimal(world, context.getLivingTarget(), context.getAttacker());
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (enabled && target != null) {
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            Level world = attacker.getCommandSenderWorld();
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, level));
            target.invulnerableTime = 0;
            if (randomNum <= level) {
                spawnAnimal(world, target, attacker);
            }
        }
    }

    public void spawnAnimal(Level world, Entity entity, LivingEntity summoner) {
        if (enabled) {
            LoyalZombie zombie = TFEntities.LOYAL_ZOMBIE.get().create(world);
            if (summoner.getLevel().getEntitiesOfClass(LoyalZombie.class,new AABB(summoner.blockPosition()).inflate(15)).size()<11) {
                int ability =1,time=1200;
                world.addFreshEntity(zombie);
                zombie.moveTo(entity.getX(), entity.getY(), entity.getZ());
                zombie.spawnAnim();
                zombie.setTame(true);
                zombie.setOwnerUUID(summoner.getUUID());
                if (ModifierLevel.EquipHasModifierlevel(summoner, CloudertinkerModifiers.zombietoba.getId())) {
                    ability=4;time=1800;
                    zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TFItems.IRONWOOD_HELMET.get()));
                    zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TFItems.IRONWOOD_CHESTPLATE.get()));
                    zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(TFItems.IRONWOOD_LEGGINGS.get()));
                    zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(TFItems.IRONWOOD_BOOTS.get()));
                    zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TFItems.IRONWOOD_SWORD.get()));
                    zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, time, 0));
                }
                zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, time, ability));
                zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, time, ability));
                zombie.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 1));

                summoner.gameEvent(GameEvent.ENTITY_PLACE, summoner);


                zombie.playSound(TFSounds.LOYAL_ZOMBIE_SUMMON.get(), 1.0F, zombie.getVoicePitch());
            }
        }
    }

}