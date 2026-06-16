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
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Consumer;

public class FlameOfArmorBurning extends BattleModifier {
    //？？
//
//攻击对目标的护甲造成1000点耐久值损伤
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
        if (!(toolAttackContext.getAttacker() instanceof Player))
            return v1;
        Player p = (Player) toolAttackContext.getAttacker();
        if (unit != null) {
            for (ItemStack i : unit.getArmorSlots()) {
                if (i != null)
                    i.hurtAndBreak(1000, p, (fuckThisAllShitWorldUsingThisFuckSuperFUCKING_SHIT_WhyNOT_USING_CSHARP_ThatsBetterFuckingCoding) -> {
                    });
            }
        }
        return v1;
    }

}
