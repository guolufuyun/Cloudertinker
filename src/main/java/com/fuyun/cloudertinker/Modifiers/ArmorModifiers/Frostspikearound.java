package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;


import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;
import twilightforest.init.TFMobEffects;

import java.util.List;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Frostcraft.frostcraft;

public class Frostspikearound extends NoLevelsModifier implements ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MODIFY_DAMAGE);
    }

    @Override
    public float modifyDamageTaken(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext event, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        LivingEntity attacker=null;
        if (damageSource.getEntity() instanceof LivingEntity living)attacker=living;
        if (damageSource.getEntity() instanceof Projectile projectile&&projectile.getOwner() instanceof LivingEntity livingEntity)attacker=livingEntity;
        if (attacker != null&&attacker!=event.getEntity()&&damageSource instanceof EntityDamageSource entityDamageSource &&!entityDamageSource.isThorns()){
            LivingEntity entity=event.getEntity();
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            ModDataNBT tooldata = iToolStackView.getPersistentData();
            int ice = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            int current = overslime.getShield(iToolStackView);
            if (ice>0&&tooldata.getInt(frostcraft)>=20){
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                List<Mob> mobList = entity.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + 3, y + 3, z + 3, x - 3, y - 3, z - 3));
                for (Mob mob : mobList){
                    if (mob != null&&mob!=attacker){
                        react(attacker,mob,((float)current)/75 );
                    }
                    react(attacker,entity,((float)current)/75);
                }
            }else if(current>20){
            react(attacker,entity,((float)current)/100 );
                overslime.addOverslime(iToolStackView,modifierEntry, -20);
            }
        }
        return v;
    }
    public void react(LivingEntity enemy,LivingEntity entity,float damage){
        enemy.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 100 ,  1));
        enemy.invulnerableTime = 0;
        enemy.hurt(DamageSource.thorns(entity),damage);
        enemy.invulnerableTime = 0;
        enemy.setLastHurtByMob(entity);
    }
}
