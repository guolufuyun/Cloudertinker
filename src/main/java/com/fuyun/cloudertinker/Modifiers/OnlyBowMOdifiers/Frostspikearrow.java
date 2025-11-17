package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;
import twilightforest.init.TFMobEffects;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Frostcraft.frostcraft;

public class Frostspikearrow extends NoLevelsModifier implements ProjectileLaunchModifierHook, ProjectileHitModifierHook {
    public static final ResourceLocation frostspike = Cloudertinker.getResource("frostspike");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT,ModifierHooks.PROJECTILE_LAUNCH);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target!=null&&projectile instanceof AbstractArrow arrow){
            ModDataNBT arrowdata = ModDataNBT.readFromNBT(arrow.getPersistentData());
            if (arrowdata.getInt(frostspike)>0){
            target.addEffect(new MobEffectInstance(TinkerModifiers.bleeding.get(), 100 ,  (int)arrowdata.getInt(frostspike)));
            target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 100 ,  (int)arrowdata.getInt(frostspike)));
            target.addEffect(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 100 ,  (int)arrowdata.getInt(frostspike)));
            }
            if (arrowdata.getInt(frostspike)>=2){
                arrow.setBaseDamage(arrow.getBaseDamage()*1.2);
                arrow.setPierceLevel((byte)(arrow.getPierceLevel()+1));
                arrowdata.putInt(frostspike,arrowdata.getInt(frostspike)+1);
            }
        }

        return false;
    }

    @Override
    public void onProjectileLaunch(IToolStackView iToolStackView, ModifierEntry modifierEntry, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean b) {
        if (projectile instanceof AbstractArrow arrow){
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            ModDataNBT tooldata = iToolStackView.getPersistentData();
            ModDataNBT arrowdata = ModDataNBT.readFromNBT(arrow.getPersistentData());
            int ice = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            int current = overslime.getShield(iToolStackView);
            if (ice>0&&tooldata.getInt(frostcraft)>=30){
                arrow.setPierceLevel((byte)(arrow.getPierceLevel()+1));
                arrowdata.putInt(frostspike,2);
            }
            else if(current>30){
                arrow.setPierceLevel((byte)(arrow.getPierceLevel()+1));
                overslime.addOverslime(iToolStackView,modifierEntry, -30);
                arrowdata.putInt(frostspike,1);
            }

        }
    }
}
