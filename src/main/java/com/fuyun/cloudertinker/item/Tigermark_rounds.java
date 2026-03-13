package com.fuyun.cloudertinker.item;

import com.fuyun.cloudertinker.CTKConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public abstract class Tigermark_rounds extends Item {
    public float damageboost= 1.0f;
    public int thrust= 50;
    public double push_power= CTKConfig.COMMON.Push_Power.get();
    public float explosion_damage= CTKConfig.COMMON.Explosion_damage.get().floatValue();
    public ChatFormatting color=ChatFormatting.RED;

    public Tigermark_rounds(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()){
            list.add(Component.translatable("cloudertinker.item.tooltip.round.value1",this.damageboost*100).withStyle(this.color));
            list.add(Component.translatable("cloudertinker.item.tooltip.round.value2",this.thrust).withStyle(this.color));
            list.add(Component.translatable("cloudertinker.item.tooltip.round.value3",this.push_power).withStyle(this.color));
            list.add(Component.translatable("cloudertinker.item.tooltip.round.value4",this.explosion_damage*100).withStyle(this.color));
        }else {
            list.add(Component.translatable("cloudertinker.item.tooltip.holdshift").withStyle(this.color));
        }
        super.appendHoverText(stack, level, list, flag);
    }

    public void onMeleeDamage(IToolStackView tool, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {}
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){}
    public void onCharge(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int thrust){}
    public void onChargeHit(IToolStackView tool, ModifierEntry modifier, LivingEntity attacker,LivingEntity target, int thrust ,List<Mob> mobList){}
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int thrust,List<Mob> mobList){}
    public void onFillRound(IToolStackView tool, Player player){}

}
