/*
 * @Author: w 3519533277@qq.com
 * @Date: 2026-06-15 17:43:49
 * @LastEditors: w 3519533277@qq.com
 * @LastEditTime: 2026-06-15 17:45:33
 * @FilePath: \Cloudertinker\src\main\java\com\fuyun\cloudertinker\Modifiers\WarpenModifiers\BlueBurnAbility.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.Effects.BlueBurn;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class BlueBurnAbility extends BattleModifier {
    //蓝焰
//
//攻击施加18秒蓝焰状态，使其如果处于燃烧状态，额外受到武器攻击力百分比的伤害
    public boolean havenolevel() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }

    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        LivingEntity unit = toolAttackContext.getLivingTarget();
        if (unit == null)
            return v1;
        //........
        unit.setRemainingFireTicks(unit.getRemainingFireTicks()+361);
        unit.addEffect(new MobEffectInstance(CloudertinkerEffects.BlueBurn.get(),360,0));
        BlueBurn b = null;
        MobEffectInstance effect = unit.getEffect(CloudertinkerEffects.BlueBurn.get());
        if (effect != null && effect.getEffect() instanceof BlueBurn) {
            b = (BlueBurn) effect.getEffect();
        }

        //........
        if (b == null) return v1;
        if (b.GetVal() < v1)
            b.WeaponAttack = v1;

        return v1;
    }

}
