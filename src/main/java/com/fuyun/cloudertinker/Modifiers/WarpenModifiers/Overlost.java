package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

public class Overlost extends BattleModifier {
    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (toolAttackContext.getLivingTarget() != null) {
            //转换。由于已经是LivingEntity类了所以实际上这个句子只会更改命名，后面就可以用entity来指代toolAttackContext.getLivingTarget()
            LivingEntity entity = toolAttackContext.getLivingTarget();
            MobEffectInstance instance = entity.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            int current = overslime.getShield(iToolStackView);
            if (instance != null) {
                int EffectLevel = entity.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
                return v1 + (v1 * 0.1f *modifierEntry.getLevel() *(EffectLevel)+1);
            }else if(current>=overslime.getShieldCapacity(iToolStackView,modifierEntry) *0.1){
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,(int) (overslime.getShieldCapacity(iToolStackView,modifierEntry) *0.1) , modifierEntry.getLevel()-1));
                overslime.addOverslime(iToolStackView,modifierEntry, -(int) (overslime.getShieldCapacity(iToolStackView,modifierEntry) *0.1));
                return v1;
            }else {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, current,  modifierEntry.getLevel()-1));
                overslime.addOverslime(iToolStackView,modifierEntry, -current);
                return v1;
            }
        }
            return v1;
        }


}
