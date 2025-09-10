package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import twilightforest.entity.projectile.SeekerArrow;

public class Seekwind extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public int getPriority() {
        // TODO: rethink ordering of ammo modifiers
        return 60; // after trick quiver, before bulk quiver, can't go after bulk due to desire to use inventory
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow1, NamespacedNBT namespacedNBT, boolean primary) {
        if (projectile instanceof AbstractArrow arrow&&shooter instanceof Player player&&primary) {
        SeekerArrow seekerArrow=new SeekerArrow(projectile.getLevel(),shooter);
            ModifierNBT modifiers = tool.getModifiers();
            seekerArrow.setBaseDamage((float) (arrow.getBaseDamage()));
            seekerArrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(cap -> cap.setModifiers(modifiers));
            seekerArrow.setPos(arrow.getX(), arrow.getY(), arrow.getZ());
            seekerArrow.setOwner(player);
            seekerArrow.setDeltaMovement(arrow.getDeltaMovement());
            player.level.addFreshEntity(seekerArrow);


        }
        if (arrow1 != null) {
            arrow1.discard();
        }
    }



}
