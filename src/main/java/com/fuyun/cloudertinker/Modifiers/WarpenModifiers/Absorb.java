package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Absorb extends BattleModifier {
    private static boolean needsRepair(IToolStackView tool) {
        return tool.getDamage() > 0 && !tool.isBroken();
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null&&context.getAttacker()instanceof Player player) {
            ArrayList<ToolStack> ironwoodlist = new ArrayList<>(List.of());
            var slotlist = Arrays.stream(EquipmentSlot.values()).toList();
           for (EquipmentSlot slot :slotlist ) {
              var itemStack1=player.getItemBySlot(slot);
                   var materials = ToolStack.from(itemStack1).getMaterials();
                   for (MaterialVariant variant: materials.getList()) {
                       if (variant.getVariant().getId().getPath().equals("ironwood"))
                           ironwoodlist.add(ToolStack.from(itemStack1));
                       break;
                   }
           }
            for (ToolStack toolStack:ironwoodlist){
                if (!needsRepair(tool)) continue;
                ToolDamageUtil.repair(toolStack, averageInt(damageDealt * 0.05f*modifier.getLevel())/ironwoodlist.size());
            }
            while (!ironwoodlist.isEmpty()){
                ironwoodlist.remove(0);
            }
        }
    }
    private static int averageInt(float value) {
        double floor = Math.floor(value);
        double rem = value - floor;
        return (int) floor + (Math.random() < rem ? 1 : 0);
    }
}
