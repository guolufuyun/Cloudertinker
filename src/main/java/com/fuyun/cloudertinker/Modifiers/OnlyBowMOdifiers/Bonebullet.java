package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.shared.TinkerEffects;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class Bonebullet extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, ModDataNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null) {
            target.addEffect(new MobEffectInstance(TinkerEffects.bleeding.get(),600, arrow.getPierceLevel()));
            arrow.setBaseDamage(arrow.getBaseDamage()*1.3);
//            arrow.setPierceLevel((byte)(arrow.getPierceLevel()+level));
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT namespacedNBT, boolean primary) {
        if (arrow != null) {
            arrow.setBaseDamage(arrow.getBaseDamage()*1.3);
        }
    }
}
