package com.fuyun.cloudertinker.Modifiers.anvil.Slots;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class Mysteriousveil extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void addVolatileData(IToolContext iToolContext, @NotNull ModifierEntry modifierEntry, ModDataNBT modDataNBT) {
        modDataNBT.addSlots(SlotType.ABILITY,1);
    }
    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }
}
