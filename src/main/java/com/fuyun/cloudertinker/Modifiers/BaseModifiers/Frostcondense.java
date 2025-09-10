package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import util.method.ModifierLevel;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Frostcraft.frostcraft;

public class Frostcondense extends BattleModifier {
    public Frostcondense() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingDeathEvent);
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = modifier.getLevel();
            int level1 = tool.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            if (player.tickCount %20 ==0 &&tooldata.getInt(frostcraft)<level1 * 30 + 30 ){
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)+level);

            }
            if (player.tickCount %20 ==0 &&tooldata.getInt(frostcraft)<0){tooldata.putInt(frostcraft,0);}
        }
    }

    private void LivingDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker=event.getSource().getEntity();
        if (entity != null && attacker instanceof LivingEntity entity1 ){
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.frostcondense.getId())) {
                ModDataNBT tool = ToolStack.from(entity1.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
                int level1 = ToolStack.from(entity1.getItemBySlot(EquipmentSlot.MAINHAND)).getModifierLevel(CloudertinkerModifiers.frostcondense.getId());
                tool.putInt(frostcraft, tool.getInt(frostcraft) + (20 * level1));
            }
        }

    }
}
