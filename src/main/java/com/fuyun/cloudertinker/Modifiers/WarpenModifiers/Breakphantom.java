package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import util.method.ModifierLevel;

public class Breakphantom extends BattleModifier implements MeleeDamageModifierHook {
    public boolean havenolevel() {
        return true;
    }
    public Breakphantom() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingDeathEvent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }


    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (context.getLivingTarget() != null) {
            LivingEntity player = context.getAttacker();
            LivingEntity entity = context.getLivingTarget();
            MobEffectInstance instance =player.getEffect(MobEffects.INVISIBILITY);
            if (instance!=null){
                entity.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 100  ,  2));
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 ,  2));
                player.removeEffect(MobEffects.INVISIBILITY);
                return damage * 2.5f ;
            }
        }
        return damage ;
    }

    private void LivingDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker=entity.getLastHurtByMob();
        if ( attacker instanceof LivingEntity entity1 ){
            if (ModifierLevel.EquipHasModifierlevel(entity1, CloudertinkerModifiers.breakphantom.getId())) {
                entity1.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 400, 0));
            }
        }
        }
    }


