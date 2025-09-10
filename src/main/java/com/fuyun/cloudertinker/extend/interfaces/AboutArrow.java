package com.fuyun.cloudertinker.extend.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public interface AboutArrow extends ProjectileHitModifierHook, ProjectileLaunchModifierHook {
    default void initarrowinterface(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.PROJECTILE_LAUNCH);
    }

    default boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target != null && attacker != null && projectile instanceof AbstractArrow arrow) {
            this.arrowhurt(modifiers, persistentData, modifier.getLevel(), projectile, hit, arrow, attacker, target);
        }

        return false;
    }

    default void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (arrow != null) {
            this.onTinkerArrowShoot(tool, modifier.getLevel(), shooter, projectile, arrow, namespacedNBT, primary);
        }

    }

    default void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
    }

    default void onTinkerArrowShoot(IToolStackView tool, int level, LivingEntity shooter, Projectile projectile, AbstractArrow arrow, NamespacedNBT namespacedNBT, boolean primary) {
    }
}
