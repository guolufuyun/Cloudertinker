package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import util.method.ModifierLevel;

public class Knight_honorable extends ArmorModifier  {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
//        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
    }
//    @Override
//    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
//        if (enemy != null) {
//            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 ,  level));
//        }
//        return amount;
//    }
//
//    @Override
//    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
//        if (toolAttackContext.isFullyCharged() && toolAttackContext.getLivingTarget() != null) {
//            LivingEntity entity = toolAttackContext.getAttacker();
//            int level = modifierEntry.getLevel();
//            if (level <= 3){
//                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10  ,  level-1));
//                     } else {
//                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10  ,  2));
//                entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100  ,  level-3));
//            }
//                 }
//        return v1;
//    }
    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker=event.getSource().getEntity();
        if (ModifierLevel.EquipHasModifierlevel(entity,this.getId())&&attacker!=null){
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 , ModifierLevel.getTotalArmorModifierlevel(entity,this.getId())+ModifierLevel.getEachHandsTotalModifierlevel(entity,this.getId())));
        }
        if (attacker instanceof Player player&&ModifierLevel.EquipHasModifierlevel(player,this.getId())&&entity!=null&&player.getAttackStrengthScale(0.0f)>=0.9){
            int level=ModifierLevel.getTotalArmorModifierlevel(player,this.getId())+ModifierLevel.getEachHandsTotalModifierlevel(player,this.getId());
            if (level <= 3){
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10  ,  level-1));
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10  ,  2));
                if (player.getEffect(MobEffects.ABSORPTION)==null) player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400  ,  (level-3)/2));
            }
        }
    }
}



