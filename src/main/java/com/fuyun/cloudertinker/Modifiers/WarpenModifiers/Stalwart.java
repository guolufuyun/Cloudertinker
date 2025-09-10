package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Stalwart extends BattleModifier {
//    坚毅
public boolean havenolevel() {
    return true;
}
    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if(toolAttackContext.isCritical() && RANDOM.nextInt(10) == 0) {
            toolAttackContext.getAttacker().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200,0));
        }
        return v1;
    }


}
