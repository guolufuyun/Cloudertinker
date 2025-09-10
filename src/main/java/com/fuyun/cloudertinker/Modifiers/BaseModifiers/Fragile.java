package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class Fragile extends Modifier implements BlockBreakModifierHook, MeleeDamageModifierHook, ProjectileHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,  ModifierHooks.BLOCK_BREAK,ModifierHooks.MELEE_DAMAGE,ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public int getPriority() {
        // higher than stoneshield, overslime, and reinforced
        return 300;
    }
    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getLivingTarget()!=null&& !context.isExtraAttack()&&(context.getAttacker()instanceof Player player)&&!player.getAbilities().instabuild) {
            tool.setDamage(tool.getDamage()+100*modifier.getLevel());
            return damage;
        }
        return damage;
    }


    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        if (toolHarvestContext.getPlayer() != null&&!toolHarvestContext.getPlayer().getAbilities().instabuild ){
            tool.setDamage(tool.getDamage()+100*modifierEntry.getLevel());
        }
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker instanceof Player player&&!player.getAbilities().instabuild)ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).setDamage(ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getDamage()+100*modifier.getLevel());
        return false;
    }
}
