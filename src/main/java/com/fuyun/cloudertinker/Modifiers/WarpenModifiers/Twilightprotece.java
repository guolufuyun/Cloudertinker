package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;

import java.util.List;

import static com.fuyun.cloudertinker.Effects.ShildCooldown.shield_cooldown;


public class Twilightprotece extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }
    public static boolean enabled = ModList.get().isLoaded("twilightforest");




    public @NotNull InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource == InteractionSource.RIGHT_CLICK) {
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    private void eat(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (entity instanceof Player player) {
                if (ToolDamageUtil.directDamage(tool, 100 , player, player.getUseItem())) {
                    player.broadcastBreakEvent(player.getUsedItemHand());
                }

        }

    }

    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(player.getPersistentData());
            MobEffectInstance instance =player.getEffect(CloudertinkerEffects.ShildCooldown.get());
            if (instance != null||entitydata.getInt(shield_cooldown)>0) {

                player.getCooldowns().addCooldown(player.getMainHandItem().getItem(),10);
            } else {if (enabled){
                player.getCapability(CapabilityList.SHIELDS).ifPresent(IShieldCapability::replenishShields);
                player.getCooldowns().addCooldown(player.getMainHandItem().getItem(),10);
                entitydata.putInt(shield_cooldown, 1200);
                player.addEffect(new MobEffectInstance(CloudertinkerEffects.ShildCooldown.get(),1200, 0));
                this.eat(tool, modifier, entity);
            }
        }}

    }


    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.NONE;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 1;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(player.getPersistentData());
            list.add(Component.translatable("modifier.cloudertinker.shieldcccldown.tooltip", entitydata.getInt(shield_cooldown)/20).withStyle(ChatFormatting.YELLOW));
        }
    }
    }

