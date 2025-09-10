package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class MagalaTyranny extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity atteaker= context.getAttacker();
        if (atteaker.getEffect(CloudertinkerEffects.Bloodlust.get())==null&&atteaker.getEffect(CloudertinkerEffects.Bloodlust_erode.get())==null&&atteaker.getEffect(CloudertinkerEffects.Bloodlust_beat.get())==null){
            atteaker.addEffect(new MobEffectInstance(CloudertinkerEffects.Bloodlust.get(),2400,0));
        }

        return knockback;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity atteaker= context.getAttacker();
        if (atteaker.getEffect(CloudertinkerEffects.Bloodlust_beat.get())!=null){
            LivingEntity entity= context.getLivingTarget();
            if (entity != null) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.WITHER.bypassArmor().bypassMagic().bypassEnchantments(), damageDealt*0.25f);
            entity.setLastHurtByMob(context.getAttacker());
        }
    }}

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity atteaker, LivingEntity target) {
        if (atteaker.getEffect(CloudertinkerEffects.Bloodlust.get())==null&&atteaker.getEffect(CloudertinkerEffects.Bloodlust_erode.get())==null&&atteaker.getEffect(CloudertinkerEffects.Bloodlust_beat.get())==null){
            atteaker.addEffect(new MobEffectInstance(CloudertinkerEffects.Bloodlust.get(),2400,0));
        }
    }
}
