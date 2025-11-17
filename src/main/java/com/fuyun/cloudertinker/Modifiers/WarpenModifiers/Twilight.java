package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import twilightforest.world.registration.TFGenerationSettings;

public class Twilight extends BattleModifier implements MeleeDamageModifierHook, BreakSpeedModifierHook {
//    暮光
public boolean havenolevel() {
    return true;
}
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED, ModifierHooks.MELEE_DAMAGE);
    }
    public static final ResourceKey<Level> Twilight_KEY = ResourceKey.create(Registries.DIMENSION,new ResourceLocation("twilightforest","twilight_forest"));
    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        return !toolAttackContext.getAttacker().getCommandSenderWorld().dimension().equals(Twilight_KEY) ? v1 + 2.0F : v1;
    }


    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if(event.getEntity().getCommandSenderWorld().dimension().equals(Twilight_KEY)) {
            event.setNewSpeed(event.getNewSpeed() + 2.0F);
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT namespacedNBT, boolean primary) {
        if (arrow != null) {
            arrow.setBaseDamage(!shooter.getCommandSenderWorld().dimension().equals(Twilight_KEY) ? arrow.getBaseDamage() * 1.1F : arrow.getBaseDamage());
        }
    }
}
