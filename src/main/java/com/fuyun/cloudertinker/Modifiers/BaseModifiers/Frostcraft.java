package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

import java.util.List;

public class Frostcraft extends Modifier implements ModifyDamageModifierHook , MeleeHitModifierHook, ProjectileLaunchModifierHook, TooltipModifierHook, InventoryTickModifierHook, ModifierRemovalHook {
    public static final ResourceLocation frostcraft = Cloudertinker.getResource("frostcraft");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,ModifierHooks.MELEE_HIT,ModifierHooks.PROJECTILE_LAUNCH,ModifierHooks.MODIFY_DAMAGE,ModifierHooks.TOOLTIP,ModifierHooks.INVENTORY_TICK,ModifierHooks.REMOVE);
    }
    @Override
    public int getPriority() {
        // TODO: rethink ordering of ammo modifiers
        return 90; // after trick quiver, before bulk quiver, can't go after bulk due to desire to use inventory
    }
    @Override
    public void afterMeleeHit(IToolStackView iToolStackView, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        OverslimeModifier overslime = TinkerModifiers.overslime.get();
        int current = overslime.getShield(iToolStackView);
        int used= getMeleefrostused(iToolStackView);
        if (iToolStackView.getModifierLevel(CloudertinkerModifiers.overfreeze.getId())>0&&current>=10*used){
            overslime.addOverslime(iToolStackView,modifier, -(10*used));
        }else{
            tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-used);
        }

    }

    @Override
    public void onProjectileLaunch(IToolStackView iToolStackView, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @org.jetbrains.annotations.Nullable AbstractArrow arrow, NamespacedNBT namespacedNBT, boolean primary) {
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        OverslimeModifier overslime = TinkerModifiers.overslime.get();
        int current = overslime.getShield(iToolStackView);
        int used= getRangefrostused( iToolStackView);
        if (iToolStackView.getModifierLevel(CloudertinkerModifiers.overfreeze.getId())>0&&current>=10*used){
            overslime.addOverslime(iToolStackView,modifier, -(10*used));
        }else{
            tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-used);
        }
    }
    @Override
    public float modifyDamageTaken(IToolStackView iToolStackView, ModifierEntry modifier, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        if (damageSource.getEntity() instanceof LivingEntity entity) {
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            int current = overslime.getShield(iToolStackView);
            int used= getArmorfrostused(iToolStackView);
            if (iToolStackView.getModifierLevel(CloudertinkerModifiers.overfreeze.getId())>0&&current>=10*used){
                overslime.addOverslime(iToolStackView,modifier, -(10*used));
            }else{
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-used);
            }
        }
        return v;
    }

    @Nullable
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(frostcraft);
        return null;}


    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer player) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = modifier.getLevel();
            if (player.tickCount %(40/(level)) ==0 &&tooldata.getInt(frostcraft)<level * 30 + 30 && !isSelected ){
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)+1);

            }
            if (player.tickCount %20 ==0 &&tooldata.getInt(frostcraft)<0){tooldata.putInt(frostcraft,0);}
            if (player.tickCount %2 ==0 &&tooldata.getInt(frostcraft)>level * 30 + 30){tooldata.putInt(frostcraft,level * 30 + 30);}
        }
    }
public static void Randomfrostuse (IToolStackView iToolStackView,int usenum) {
    int level = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
    ModDataNBT tooldata = iToolStackView.getPersistentData();
    if (tooldata.getInt(frostcraft)>=usenum){
        tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-usenum);
    }
}
public int getMeleefrostused (IToolStackView iToolStackView) {
    int level = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
    ModDataNBT tooldata = iToolStackView.getPersistentData();
        int use=0;
        if (iToolStackView.getModifierLevel(CloudertinkerModifiers.frostsword.getId())>0){
            if (level < 3){
                use+=9;
            } else if (level <6) {
                use+=18;
            }else if(level<9){
                use+=27;
            }else {
                use+=33;
            }
        }

    return use;
}
    public int getRangefrostused (IToolStackView iToolStackView) {
        int level = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        int use=0;
        if (iToolStackView.getModifierLevel(CloudertinkerModifiers.frostbomb.getId())>0){
            use+=3 *level+3;
        }
        return use;
    }
    public int getArmorfrostused (IToolStackView iToolStackView) {
        int level = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        int use=0;
        if (iToolStackView.getModifierLevel(CloudertinkerModifiers.frostaround.getId())>0){
            use+=9 *level;
        }
        return use;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            list.add(Component.translatable("modifier.cloudertinker.frostcraft.tooltip", tooldata.getInt(frostcraft),modifier.getLevel()* 30 + 30).withStyle(ChatFormatting.AQUA));
            if (tool.hasTag(TinkerTags.Items.MELEE)){
                list.add(Component.translatable("modifier.cloudertinker.frostcraftmelee.tooltip",getMeleefrostused( tool)).withStyle(ChatFormatting.AQUA));
            }
            if (tool.hasTag(TinkerTags.Items.RANGED)){
                list.add(Component.translatable("modifier.cloudertinker.frostcraftrange.tooltip",getRangefrostused(tool)).withStyle(ChatFormatting.AQUA));
            }
            if (tool.hasTag(TinkerTags.Items.ARMOR)){
                list.add(Component.translatable("modifier.cloudertinker.frostcraftarmor.tooltip",getArmorfrostused( tool)).withStyle(ChatFormatting.AQUA));
            }
        }
    }
}
