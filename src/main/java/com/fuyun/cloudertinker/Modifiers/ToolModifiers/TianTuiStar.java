package com.fuyun.cloudertinker.Modifiers.ToolModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.item.Tigermark_rounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.ArrayList;
import java.util.List;


public class TianTuiStar extends NoLevelsModifier implements GeneralInteractionModifierHook, TooltipModifierHook, MeleeDamageModifierHook, MeleeHitModifierHook {
    private static final ResourceLocation thrust= Cloudertinker.getResource("thrust");
    private static final ResourceLocation round_num= Cloudertinker.getResource("round_num");
    private static final ResourceLocation round_type = Cloudertinker.getResource("round_type");
    private final List<Mob> moblist1 = new ArrayList<>();
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,  ModifierHooks.GENERAL_INTERACT, ModifierHooks.TOOLTIP,ModifierHooks.MELEE_DAMAGE,ModifierHooks.MELEE_HIT);
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
            Level level = entity.getCommandSenderWorld();
            Vec3 lookVec = player.getLookAngle().normalize();
            double pushp = 0;
                boolean influid = entity.isInFluidType();
                if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(tooldata.getString(round_type))) instanceof Tigermark_rounds round) {
                    pushp = round.push_power;
                    if(influid)pushp*=0.5;
                    round.onCharge(tool, modifier, player,tooldata.getInt(thrust));
                double pushX = lookVec.x * pushp;
            double pushY = lookVec.y * pushp * 0.1;
            double pushZ = lookVec.z * pushp;
            player.push(pushX, pushY, pushZ);
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            double a=0.5+(0.5*tool.getModifierLevel(TinkerModifiers.expanded.get()));
            List<Mob> mobList = entity.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + a, y + 2, z + a, x - a, y - 1, z - a));
            for (Mob mob : mobList){
                if (mob != null&& mob.isAlive()&&!moblist1.contains(mob)){

                    if (ToolAttackUtil.attackEntity(tool, player, InteractionHand.MAIN_HAND, mob, () -> 1.0, true)) {
                        moblist1.add(mob);
                        round.onChargeHit(tool,modifier,entity,mob,tooldata.getInt(thrust),moblist1);
                    }
                }
            }}
            if (level.isClientSide) {
                // 可以添加粒子效果等视觉反馈
            }
        }else{
            fillround(tool,player);
        } 
        }
    }
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.BOW;
    }
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 32767;
    }
    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(tool.getPersistentData().getString(round_type))) instanceof Tigermark_rounds round){
            round.onStoppedUsing(tool,modifier,entity,tool.getPersistentData().getInt(thrust),moblist1);
        }
        moblist1.clear();
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(tool.getPersistentData().getString(round_type))) instanceof Tigermark_rounds round){
            round.onMeleeDamage(tool,modifierEntry,toolAttackContext,v,v1);
            return (float) (v1*round.damageboost);
        }
        return v1;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        ModDataNBT tooldata = tool.getPersistentData();
        if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(tool.getPersistentData().getString(round_type))) instanceof Tigermark_rounds round&&tooldata.getInt(thrust)>0&&context.getLivingTarget()!=null&&context.getLivingTarget().isAlive()&&context.getAttacker() instanceof Player player){
           context.getLivingTarget().invulnerableTime = 0;
            if (context.isCritical()){
              context.getLivingTarget().hurt(DamageSource.explosion(context.getAttacker()),damageDealt*round.explosion_damage);
              if (!fillround(tool,player)){
                  tooldata.putInt(thrust,0);
              }
                context.getLivingTarget().playSound(SoundEvents.GENERIC_EXPLODE,1,1);
                context.getLivingTarget().setRemainingFireTicks(200);
                context.getLivingTarget().setLastHurtByMob(player);
                if (context.getLivingTarget().getCommandSenderWorld() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION,context.getLivingTarget().getX(),context.getLivingTarget().getY()+0.5*context.getLivingTarget().getBbHeight(),context.getLivingTarget().getZ(),2 ,0,0,0,0);
                }
            }else {
                context.getLivingTarget().setRemainingFireTicks(100);
            }
            round.afterMeleeHit(tool,modifier,context,damageDealt);
        }
    }

    private boolean fillround(IToolStackView tool, Player player){
        ModDataNBT tooldata = tool.getPersistentData();
        moblist1.clear();
        if (tooldata.getInt(round_num)<8){
            for (int i = 0; i < 9; i++) {
                NonNullList<ItemStack> playerInv = player.getInventory().items;
                ItemStack invSlot = playerInv.get(i);
                if (invSlot.getItem() instanceof Tigermark_rounds round){
                    invSlot.setCount(invSlot.getCount()-1);
                    ResourceLocation itemRegistryName =  ForgeRegistries.ITEMS.getKey(round);
                    tooldata.putString(round_type, itemRegistryName.toString());
                    tooldata.putInt(round_num,tooldata.getInt(round_num)+1);
                    tooldata.putInt(thrust,round.thrust);
                    player.getCooldowns().addCooldown(tool.getItem(), 10);
                    round.onFillRound(tool,player);
                    return true;
                }
            }
        }else {
            tooldata.putInt(round_num,0);
            tooldata.putString(round_type, "");
            player.getCooldowns().addCooldown(tool.getItem(), 100);
        }
        return false;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            String roundtype = tooldata.getString(round_type);
            Component roundName=Component.translatable("modifier.cloudertinker.tiantuistar.type0");
            ChatFormatting color=ChatFormatting.WHITE;
            int thrustmax=0;
            if (!roundtype.isEmpty()) {
                Item roundItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(roundtype));
                if (roundItem instanceof Tigermark_rounds round) {
                    roundName = roundItem.getName(new ItemStack(roundItem));
                    thrustmax=round.thrust;
                    color = round.color;
                }
            }
            list.add(Component.translatable("modifier.cloudertinker.tiantuistar.current_round", roundName).withStyle(color));
            list.add(Component.translatable("modifier.cloudertinker.tiantuistar.tooltip1", tooldata.getInt(thrust),thrustmax).withStyle(color));
            list.add(Component.translatable("modifier.cloudertinker.tiantuistar.tooltip2", tooldata.getInt(round_num),8).withStyle(color));
        }
    }


}