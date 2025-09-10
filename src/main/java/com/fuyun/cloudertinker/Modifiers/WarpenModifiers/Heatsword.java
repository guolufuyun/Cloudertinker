package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class Heatsword extends BattleModifier {

    public static final ResourceLocation heatsword = Cloudertinker.getResource("heatsword");
    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(heatsword);
        return null;}
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT tooldata = tool.getPersistentData();

            if (player.tickCount %20 ==0 ){
                if (tooldata.getInt(heatsword)>100&& (isSelected ||isCorrectSlot))player.setRemainingFireTicks(200);
                if (tooldata.getInt(heatsword)<200&&player.isOnFire() && (isSelected ||isCorrectSlot))tooldata.putInt(heatsword,tooldata.getInt(heatsword)+1);
                else if (tooldata.getInt(heatsword)>=200&&player.isOnFire())tooldata.putInt(heatsword,200);
                else if (tooldata.getInt(heatsword)>0)tooldata.putInt(heatsword,tooldata.getInt(heatsword)-1);
                else tooldata.putInt(heatsword,0);
            }

        }
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        ModDataNBT tooldata = tool.getPersistentData();
        if (context.isFullyCharged()){
            tooldata.putInt(heatsword,tooldata.getInt(heatsword)+3);
        }
        if (tooldata.getInt(heatsword)<=200) {
            return damage + (damage * 0.25f * 0.01f * tooldata.getInt(heatsword)*modifier.getLevel());
        }
        else {
            return damage + damage * 0.5f*modifier.getLevel();
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        ModDataNBT tooldata = tool.getPersistentData();
        if (tooldata.getInt(heatsword)>100&&context.getLivingTarget()!=null){
            context.getLivingTarget().hurt(DamageSource.ON_FIRE.bypassArmor(),damageDealt*0.25f*(tooldata.getInt(heatsword)-100)*0.01f);
            context.getLivingTarget().setRemainingFireTicks((6 * (tooldata.getInt(heatsword)-100)));
        }
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        ModDataNBT tooldata = tool.getPersistentData();
        if (tooldata.getInt(heatsword)>100) {
            consumer.accept(Attributes.ARMOR, new AttributeModifier(UUID.fromString("3a3fe6a5-fea7-4ed1-86ea-0d2eefb801f3"), String.valueOf(Attributes.ARMOR), (int) -((tooldata.getFloat(heatsword) - 100) * 0.1), AttributeModifier.Operation.ADDITION));
        } }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            list.add(Component.translatable("modifier.cloudertinker.heatsword.tooltip1",tooldata.getInt(heatsword)).withStyle(ChatFormatting.RED));
            list.add(Component.translatable("modifier.cloudertinker.heatsword.tooltip2",tooldata.getInt(heatsword)* 0.25f * 0.01f*modifier.getLevel()).withStyle(ChatFormatting.RED));

            if (tooldata.getInt(heatsword)<=100){
                list.add(Component.translatable("modifier.cloudertinker.heatsword.tooltip3").withStyle(ChatFormatting.RED));
            }else {
                list.add(Component.translatable("modifier.cloudertinker.heatsword.tooltip4").withStyle(ChatFormatting.RED));
                list.add(Component.translatable("modifier.cloudertinker.heatsword.tooltip5",0.25f*(tooldata.getInt(heatsword)-100)).withStyle(ChatFormatting.RED));
                list.add(Component.translatable("modifier.cloudertinker.heatsword.tooltip6",(tooldata.getFloat(heatsword) - 100) * 0.1).withStyle(ChatFormatting.RED));

            }
        }
    }
}
