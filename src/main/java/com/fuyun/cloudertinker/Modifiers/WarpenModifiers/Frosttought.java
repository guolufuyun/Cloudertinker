package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import twilightforest.init.TFMobEffects;

public class Frosttought extends BattleModifier {
    public static boolean enabled = ModList.get().isLoaded("twilightforest");
    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (enabled && toolAttackContext.getLivingTarget() != null) {
            //转换。由于已经是LivingEntity类了所以实际上这个句子只会更改命名，后面就可以用entity来指代toolAttackContext.getLivingTarget()
            LivingEntity entity = toolAttackContext.getLivingTarget();
                entity.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 100 * modifierEntry.getLevel(),  modifierEntry.getLevel()+1));
        }
        return v1;
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (enabled && target != null) {
            target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(),100 * level, level+1));
        }
    }
}
