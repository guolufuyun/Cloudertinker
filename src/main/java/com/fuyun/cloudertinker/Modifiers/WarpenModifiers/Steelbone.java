package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Steelbone extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage ){
    LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getAttacker();
        if (target != null ) {
            target.invulnerableTime = 0;
           target.hurt(DamageSource.ANVIL,damage*0.5f);
            target.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(),100 , 1));
            target.setLastHurtByMob(context.getAttacker());
        }

    }

}
