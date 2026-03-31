package com.fuyun.cloudertinker.Modifiers.anvil.Slots;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;

public class Allscepters extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void addVolatileData(IToolContext iToolContext, @NotNull ModifierEntry modifierEntry, ToolDataNBT modDataNBT) {
        modDataNBT.addSlots(SlotType.DEFENSE,1);
    }
    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }


}
