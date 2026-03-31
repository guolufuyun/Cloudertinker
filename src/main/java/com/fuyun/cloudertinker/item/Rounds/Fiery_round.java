package com.fuyun.cloudertinker.item.Rounds;


import com.fuyun.cloudertinker.item.Tigermark_rounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.List;

public class Fiery_round extends Tigermark_rounds {
    public Fiery_round(Properties pProperties) {
        super(pProperties);
    this.damageboost= 1.0f;
        this.thrust= 100;
        this.push_power= 0.4;
        this.explosion_damage= 0.6f;
        this.color= ChatFormatting.DARK_RED;
    }
    @Override
    public float getMeleedamage(IToolStackView tool, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (toolAttackContext.getTarget() instanceof LivingEntity livingEntity&&livingEntity.isAlive()&&livingEntity.isOnFire()){
            return v1*(this.getDamageBoost()*1.5f);
        }
        return v1*this.getDamageBoost();
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("cloudertinker.item.tooltip.fround.value").withStyle(this.color));
        }
    }
}
