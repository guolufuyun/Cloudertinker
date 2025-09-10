package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.init.TFDamageSources;


public class Sacrificeblood extends ArmorModifier {
    public static boolean enabled = ModList.get().isLoaded("twilightforest");
    private static final ResourceLocation sbcooldown = Cloudertinker.getResource("sbcooldown");

    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(sbcooldown);
        return null;}

    public @NotNull InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource == InteractionSource.RIGHT_CLICK) {
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {

        if (tool.getCurrentDurability() > 5 && entity instanceof ServerPlayer player) {
            MobEffectInstance instance =player.getEffect(MobEffects.DAMAGE_BOOST);
            ModDataNBT tooldata = tool.getPersistentData();
        if (enabled && instance != null) {
            int timeleft = player.getEffect(MobEffects.DAMAGE_BOOST).getDuration();
            int EffectLevel = player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
            player.hurt(new DamageSource(TFDamageSources.tfSource("expired")), 5);
            ToolDamageUtil.damageAnimated(tool, 5 , entity);
            tooldata.putInt(sbcooldown,1);
            player.invulnerableTime = 0;
            if (timeleft >= 0&& EffectLevel<=9) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, timeleft + 20 + 30 * modifier.getLevel() -30, EffectLevel + 1 ));
            }else{
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, timeleft + 20 + 30 * modifier.getLevel() -30, 10 ));
            }

        } else {if (enabled){
            player.hurt(new DamageSource(TFDamageSources.tfSource("expired")), 5 * modifier.getLevel() );
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200 * modifier.getLevel(), modifier.getLevel() - 1));
            ToolDamageUtil.damageAnimated(tool, 5, entity);
            tooldata.putInt(sbcooldown,1);
            player.invulnerableTime = 0;
        }}
    }
}

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT tooldata = tool.getPersistentData();
            if (player.tickCount %2 ==0 ){
                tooldata.putInt(sbcooldown,tooldata.getInt(sbcooldown)-1);
                if (tooldata.getInt(sbcooldown) == 0) {
                    player.getCooldowns().addCooldown(player.getMainHandItem().getItem(),10);
                }
            }
        }
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.NONE;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 1;
    }
}