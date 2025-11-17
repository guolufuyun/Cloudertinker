package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.capacity.OverslimeModule;
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
            OverslimeModule overslimeModule = OverslimeModule.INSTANCE; // 使用模块实例
            int current = overslimeModule.getAmount(iToolStackView); // 使用新API
            if (instance != null) {
                int EffectLevel = entity.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
                return v1 + Math.min((v1 * 0.1f * modifierEntry.getLevel() * (EffectLevel + 1)),v1*2); // 修复运算符优先级
            } else if (current >= overslimeModule.getCapacity(iToolStackView, modifierEntry) * 0.1) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (overslimeModule.getCapacity(iToolStackView, modifierEntry) * 0.1), modifierEntry.getLevel() - 1));
                overslimeModule.removeAmount(iToolStackView, (int) (overslimeModule.getCapacity(iToolStackView, modifierEntry) * 0.1)); // 使用新API
                return v1;
            } else {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, current, modifierEntry.getLevel() - 1));
                overslimeModule.removeAmount(iToolStackView, current); // 使用新API
                return v1;
            }
        }
        return v1;
    }

}
