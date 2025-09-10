package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.shared.TinkerMaterials;
import twilightforest.entity.monster.SkeletonDruid;

import java.util.Random;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Frostcraft.frostcraft;

public class Frostbone extends BattleModifier {
    public Frostbone() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingDeathEvent);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage ) {
        if (context.getLivingTarget() != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            int level = modifier.getLevel();
            LivingEntity entity= context.getLivingTarget();
            if (randomNum<=tooldata.getInt(frostcraft)*0.1) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.FREEZE.bypassArmor(), 3*level);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 ,  level-1));
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-5);
                entity.setLastHurtByMob(context.getAttacker());
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            ModDataNBT tooldata = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            if (randomNum<=tooldata.getInt(frostcraft)*0.1) {
                target.hurt(DamageSource.FREEZE.bypassArmor(), 3*level);
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 ,  level-1));
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-5);
                target.invulnerableTime = 0;
                target.setLastHurtByMob(attacker);
            }
        }

    }



    private void LivingDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source=entity.getLastDamageSource();
        if (entity instanceof Stray stray){
            ModifierUtil.dropItem(stray, new ItemStack(CloudertinkerItem.forestbone.get()));}
        if (entity instanceof SkeletonDruid druid){
            ModifierUtil.dropItem(druid, new ItemStack(TinkerMaterials.venombone.get()));}

        }

        }

