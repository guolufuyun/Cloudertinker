package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import static com.fuyun.cloudertinker.Effects.Bloodlust.bloodlust;

public class Antivirus extends BattleModifier {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getAttacker();
        if (target != null&&context.isFullyCharged() ) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(attacker.getPersistentData());
            if (attacker.getEffect(CloudertinkerEffects.Bloodlust.get())!=null){
                entitydata.putInt(bloodlust,entitydata.getInt(bloodlust)+3*modifier.getLevel());
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target != null ) {
            ModDataNBT entitydata = ModDataNBT.readFromNBT(attacker.getPersistentData());
         if (attacker.getEffect(CloudertinkerEffects.Bloodlust.get())!=null){
             entitydata.putInt(bloodlust,entitydata.getInt(bloodlust)+3*level);
         }

        }
    }
}
