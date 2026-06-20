/*
 * @Author: w 3519533277@qq.com
 * @Date: 2026-06-15 17:43:49
 * @LastEditors: w 3519533277@qq.com
 * @LastEditTime: 2026-06-15 17:45:33
 * @FilePath: \Cloudertinker\src\main\java\com\fuyun\cloudertinker\Modifiers\WarpenModifiers\BlueBurnAbility.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class MeltDown extends Modifier implements MeleeDamageModifierHook {
    //？？
//
//攻击对目标的护甲造成1000点耐久值损伤
    public boolean havenolevel() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {

        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }

    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        LivingEntity unit = toolAttackContext.getLivingTarget();
        if (!(toolAttackContext.getAttacker() instanceof Player p))
            return v1;
        if (unit != null) {
            var slot = new EquipmentSlot[]{
                    EquipmentSlot.HEAD,
                    EquipmentSlot.CHEST,
                    EquipmentSlot.LEGS,
                    EquipmentSlot.FEET
            };
//            for (ItemStack i : unit.getArmorSlots()) {
//                if (i != null)
//                    i.hurtAndBreak(1000, p, (fuckThisAllShitWorldUsingThisFuckSuperFUCKING_SHIT_WhyNOT_USING_CSHARP_ThatsBetterFuckingCoding) -> {
//                    });
//            }
            boolean hasArmor = false;
            for (EquipmentSlot i : slot) {
                if (!unit.getItemBySlot(i).isEmpty()){
                    ItemStack armor = unit.getItemBySlot(i);
                    if (!armor.isEmpty()) {
                        hasArmor = true;
                        if(armor.getItem() instanceof ModifiableItem ){
                            IToolStackView tinkerarmor= ToolStack.from( armor);
                            if (!tinkerarmor.isBroken()) {
                                ToolDamageUtil.damageAnimated(tinkerarmor, 1000, unit);
                            }
                        }else if(armor.isDamageableItem()){
                            if(armor.getMaxDamage()<= armor.getDamageValue()+1000){
                                armor.shrink(1);
                                unit.broadcastBreakEvent(i);
                            }
                            else
                                armor.setDamageValue(Math.min(armor.getDamageValue() + 1000, armor.getMaxDamage()));
                        }


                    }
                }

            }
            return hasArmor ? v1 : v1 * 2;
        }
        return v1;
    }

}
