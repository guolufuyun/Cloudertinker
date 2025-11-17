package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import twilightforest.init.TFBiomes;

public class Territorialstruggle extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, ModDataNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null ) {
            if (!attacker.getCommandSenderWorld().getBiome(attacker.blockPosition()).is(TFBiomes.ENCHANTED_FOREST)) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                attacker.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
                attacker.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
            }
            arrow.setBaseDamage(arrow.getBaseDamage() + (0.5 * arrow.getBaseDamage()));
        }
    }

    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (toolAttackContext.getLivingTarget() != null ) {
            if (!toolAttackContext.getAttacker().getCommandSenderWorld().getBiome(toolAttackContext.getAttacker().blockPosition()).is(TFBiomes.ENCHANTED_FOREST)) {
                LivingEntity entity = toolAttackContext.getLivingTarget();
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 1));
                toolAttackContext.getAttacker().addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
                toolAttackContext.getAttacker().addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
            }
            return v1 *1.5f;
        }
        return v1;
    }
}
