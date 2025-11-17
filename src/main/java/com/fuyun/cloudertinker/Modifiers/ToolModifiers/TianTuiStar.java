package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.TwilightItemTier;

import java.util.ArrayList;
import java.util.List;


public class TianTuiStar extends Modifier implements GeneralInteractionModifierHook, TooltipModifierHook{
    private static final ResourceLocation thrust= Cloudertinker.getResource("thrust");
    private static final ResourceLocation round_type= Cloudertinker.getResource("round_type");
    private static final ResourceLocation round_num= Cloudertinker.getResource("round_num");
    private final List<Mob> moblist1 = new ArrayList<>();
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,  ModifierHooks.GENERAL_INTERACT, ModifierHooks.TOOLTIP);
    }
    public @NotNull InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource == InteractionSource.RIGHT_CLICK) {
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }
    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        ModDataNBT tooldata = tool.getPersistentData();
        if (entity instanceof Player player && !tool.isBroken()&&!player.getCooldowns().isOnCooldown(tool.getItem())) {
            if (tooldata.getInt(thrust)>0){
                tooldata.putInt(thrust,tooldata.getInt(thrust)-1);
            Level level = entity.level();
            Vec3 lookVec = player.getLookAngle().normalize();
            double pushp;
            if (tooldata.getInt(round_type)==1){
                pushp = 0.2f;
            } else if (tooldata.getInt(round_type)==2) {
                pushp=0.5f;
            }else {
                pushp = 0.2f;
            }
                double pushX = lookVec.x * pushp;
            double pushY = lookVec.y * pushp * 0.1;
            double pushZ = lookVec.z * pushp;
            player.push(pushX, pushY, pushZ);
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            List<Mob> mobList = entity.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + 0.5, y + 2, z + 0.5, x - 0.5, y - 1, z - 0.5));
            for (Mob mob : mobList){
                if (mob != null&& mob.isAlive()&&!moblist1.contains(mob)){
                        ToolAttackContext attackContext = ToolAttackContext.attacker(player)
                                .target(mob)
                                .slot(EquipmentSlot.MAINHAND, InteractionHand.MAIN_HAND)
                                .cooldown(1.0f)
                                .toolAttributes(tool)
                                .extraAttack()
                                .build();
                        ToolAttackUtil.performAttack(tool ,attackContext);
                        moblist1.add(mob);
                }
            }
            if (level.isClientSide) {
                // 可以添加粒子效果等视觉反馈
            }
        }else{
            fillround(tool,player);
        } 
        }
    }
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.NONE;
    }
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 32767;
    }
    private boolean fillround(IToolStackView tool,Player player){
        ModDataNBT tooldata = tool.getPersistentData();
        moblist1.clear();
        if (tooldata.getInt(round_num)<8){
            for (int i = 0; i < 9; i++) {
                NonNullList<ItemStack> playerInv = player.getInventory().items;
                ItemStack invSlot = playerInv.get(i);
                if (invSlot.is(CloudertinkerItem.tigermark_round.get())) {
                    invSlot.setCount(invSlot.getCount()-1);
                    tooldata.putInt(round_num,tooldata.getInt(round_num)+1);
                    tooldata.putInt(thrust,50);
                    tooldata.putInt(round_type,1);
                    player.getCooldowns().addCooldown(tool.getItem(), 10);
                    break;
                } else if (invSlot.is(CloudertinkerItem.savage_tigermark_round.get())) {
                    invSlot.setCount(invSlot.getCount()-1);
                    tooldata.putInt(round_num,tooldata.getInt(round_num)+1);
                    tooldata.putInt(thrust,50);
                    tooldata.putInt(round_type,2);
                    player.getCooldowns().addCooldown(tool.getItem(), 10);
                    break;
                }
            }
        }else {
            tooldata.putInt(round_num,0);
            player.getCooldowns().addCooldown(tool.getItem(), 200);
        }
        
        return false;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();

        }
    }
}