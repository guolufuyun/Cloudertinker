package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.capacity.OverslimeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.shared.TinkerEffects;
import twilightforest.init.TFMobEffects;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Frostcraft.frostcraft;

public class Frostspikeburst extends NoLevelsModifier implements MeleeDamageModifierHook, MeleeHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {

        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE,ModifierHooks.MELEE_HIT);
    }
    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        LivingEntity entity=toolAttackContext.getLivingTarget();
        if (entity != null) {
            OverslimeModule overslimeModule = OverslimeModule.INSTANCE; // 使用模块实例
            int current = overslimeModule.getAmount(iToolStackView); // 使用新API
            ModDataNBT tooldata = iToolStackView.getPersistentData();

            int ice = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());

            if (ice > 0 && tooldata.getInt(frostcraft) >= 3) {
                return v1*1.25f*1.25f;
            } else if (current > 30) {
              return v1*1.25f;
            }
            return v1;
        }
        return v1;
    }

    @Override
    public void afterMeleeHit(IToolStackView iToolStackView, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        OverslimeModule overslimeModule = OverslimeModule.INSTANCE; // 使用模块实例
        int current = overslimeModule.getAmount(iToolStackView); // 使用新API
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        int ice = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());

        LivingEntity entity=context.getLivingTarget();
        if (entity != null){
            if (ice > 0 && tooldata.getInt(frostcraft) >= 3) {
                entity.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 300, 3));
                entity.addEffect(new MobEffectInstance(TinkerEffects.bleeding.get(), 300 , 4));
                entity.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 300 , 3));
            } else if (current > 30) {
                overslimeModule.removeAmount(iToolStackView, 30);
                entity.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 100, 1));
                entity.addEffect(new MobEffectInstance(TinkerEffects.bleeding.get(), 100 , 2));
            }
        }

    }
}
