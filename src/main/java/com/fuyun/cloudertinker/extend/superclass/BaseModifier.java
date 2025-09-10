package com.fuyun.cloudertinker.extend.superclass;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public abstract class BaseModifier extends Modifier {
    public ModDataNBT getData(IToolStackView tool) {
        return tool.getPersistentData();
    }

    public boolean havenolevel() {
        return false;
    }

    public boolean hidden() {
        return false;
    }

    public @NotNull Component getDisplayName(int level) {
        if (havenolevel()) {
            return super.getDisplayName();
        } else
            return super.getDisplayName(level);
    }

    public boolean shouldDisplay(boolean advanced) {
        if (hidden()) {
            return advanced;
        } else
            return true;
    }

    public TinkerDataCapability.TinkerDataKey<Integer> useKey() {
        return null;
    }

    public void onUseKeyEquip(LivingEntity entity, int level) {
        if (this.useKey() != null) {
            entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                if ((Integer) holder.get(this.useKey(), 0) < level) {
                    holder.put(this.useKey(), level);
                }

            });
        }

    }

    public void onUseKeyUnequip(LivingEntity entity) {
        if (this.useKey() != null) {
            entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                holder.remove(this.useKey());
            });
        }

    }

    public abstract void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, ToolHarvestContext context);
}
