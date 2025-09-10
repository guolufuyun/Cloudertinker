package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.tools.TinkerModifiers;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Resentment.resentment;

public class Brokenimpulse extends BattleModifier {

    public boolean havenolevel() {
        return true;
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getAttacker();
        if (target != null ) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(attacker.getPersistentData());
            ModDataNBT tooldata = tool.getPersistentData();
            MobEffectInstance instance = target.getEffect(TinkerModifiers.bleeding.get());
            MobEffectInstance instance1 = target.getEffect(CloudertinkerEffects.Evilmarecurse.get());
            if (instance != null && instance1 == null&&tooldata.getInt(resentment)>=20) {
                int timeleft = attacker.getEffect(TinkerModifiers.bleeding.get()).getDuration();
                int EffectLevel = attacker.getEffect(TinkerModifiers.bleeding.get()).getAmplifier();
                target.invulnerableTime = 0;
                target.hurt(DamageSource.WITHER.bypassArmor().bypassMagic().bypassEnchantments(), 5);
                target.addEffect(new MobEffectInstance(CloudertinkerEffects.Evilmarecurse.get(), timeleft, EffectLevel));
                entitydata.putInt(resentment, entitydata.getInt(resentment)-20);
                target.setLastHurtByMob(attacker);
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null ) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(attacker.getPersistentData());
            ModDataNBT tooldata = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
            MobEffectInstance instance = target.getEffect(TinkerModifiers.bleeding.get());
            MobEffectInstance instance1 = target.getEffect(CloudertinkerEffects.Evilmarecurse.get());
            if (instance != null && instance1 == null&&tooldata.getInt(resentment)>=20) {
                int timeleft = attacker.getEffect(TinkerModifiers.bleeding.get()).getDuration();
                int EffectLevel = attacker.getEffect(TinkerModifiers.bleeding.get()).getAmplifier();
                target.invulnerableTime = 0;
                target.hurt(DamageSource.WITHER.bypassArmor().bypassMagic().bypassEnchantments(), 5);
                target.addEffect(new MobEffectInstance(CloudertinkerEffects.Evilmarecurse.get(), timeleft, EffectLevel));
                entitydata.putInt(resentment, entitydata.getInt(resentment)-20);
                target.setLastHurtByMob(attacker);
            }
        }
    }
}
