package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import twilightforest.entity.projectile.SeekerArrow;
import util.method.ModifierLevel;

public class Seekwind extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public int getPriority() {
        // TODO: rethink ordering of ammo modifiers
        return 1; // after trick quiver, before bulk quiver, can't go after bulk due to desire to use inventory
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow1, ModDataNBT namespacedNBT, boolean primary) {
        if (projectile instanceof AbstractArrow arrow&&shooter instanceof Player player&&primary) {
        SeekerArrow seekerArrow=new SeekerArrow(projectile.getCommandSenderWorld(),shooter);
            ModifierNBT modifiers = tool.getModifiers();
            CompoundTag originalNBT = arrow.saveWithoutId(new CompoundTag());
            seekerArrow.load(originalNBT);
            arrow.getCapability(EntityModifierCapability.CAPABILITY);
            seekerArrow.setBaseDamage((float) (arrow.getBaseDamage()));
            arrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(originalCap -> {
                var originalModifiers = originalCap.getModifiers();
                seekerArrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(newCap ->
                        newCap.setModifiers(originalModifiers)
                );
            });
            seekerArrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(cap -> cap.addModifiers(modifiers));
            seekerArrow.setPos(arrow.getX(), arrow.getY(), arrow.getZ());
            seekerArrow.setOwner(player);
            seekerArrow.setDeltaMovement(arrow.getDeltaMovement());
            player.getCommandSenderWorld().addFreshEntity(seekerArrow);
            PersistentDataCapability.getOrWarn(seekerArrow).copyFrom(namespacedNBT.getCopy());

        }
        if (arrow1 != null) {
            arrow1.discard();
        }
    }



}
