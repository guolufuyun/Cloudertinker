package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class Posionspike extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getLivingTarget()!=null){
            LivingEntity entity= context.getLivingTarget();
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
if (randomNum<=3) {
    context.getAttacker().addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
}
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,0));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,300,0));
            entity.addEffect(new MobEffectInstance(MobEffects.POISON,200,0));
            return damage+1;
        }
        return damage+1;
    }
}
