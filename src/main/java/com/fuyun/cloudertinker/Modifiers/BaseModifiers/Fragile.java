package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.CTKConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class Fragile extends NoLevelsModifier implements BlockBreakModifierHook, MeleeDamageModifierHook, ProjectileLaunchModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,  ModifierHooks.BLOCK_BREAK,ModifierHooks.MELEE_DAMAGE,ModifierHooks.PROJECTILE_LAUNCH);
    }
    @Override
    public int getPriority() {
        // higher than stoneshield, overslime, and reinforced
        return 300;
    }
    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getLivingTarget()!=null&& !context.isExtraAttack()&&(context.getAttacker()instanceof Player player)&&!player.getAbilities().instabuild) {
            ToolDamageUtil.damageAnimated(tool, (int) (CTKConfig.COMMON.Fragile_damage.get()*0.75), context.getAttacker());
            tool.setDamage(tool.getDamage()+(int)(CTKConfig.COMMON.Fragile_damage.get()*0.25));
            return damage;
        }
        return damage;
    }


    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        if (toolHarvestContext.getPlayer() != null&&!toolHarvestContext.getPlayer().getAbilities().instabuild ){
            ToolDamageUtil.damageAnimated(tool, 75 , toolHarvestContext.getPlayer());
            tool.setDamage(tool.getDamage()+25);
        }
    }
    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifierEntry, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, ModDataNBT namespacedNBT, boolean b) {
        ToolDamageUtil.damageAnimated(tool, 75 , livingEntity);
        tool.setDamage(tool.getDamage()+25);
    }
}
