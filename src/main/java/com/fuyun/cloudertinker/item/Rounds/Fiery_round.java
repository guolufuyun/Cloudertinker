package com.fuyun.cloudertinker.item.Rounds;


import com.fuyun.cloudertinker.CTKConfig;
import com.fuyun.cloudertinker.item.Tigermark_rounds;
import net.minecraft.ChatFormatting;
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
        this.damageboost= CTKConfig.COMMON.Fiery_Damage.get().floatValue();
        this.thrust= CTKConfig.COMMON.Fiery_Thrust.get();
        this.push_power= CTKConfig.COMMON.Fiery_Push_Power.get();
        this.explosion_damage= CTKConfig.COMMON.Fiery_Explosion_damage.get().floatValue();
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
        list.add(Component.translatable("cloudertinker.item.tooltip.fround.value").withStyle(this.color));
    }
}
