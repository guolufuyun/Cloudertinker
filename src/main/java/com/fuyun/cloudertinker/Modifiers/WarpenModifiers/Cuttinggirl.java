package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class Cuttinggirl extends BattleModifier {

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null) {
            //转换。由于已经是LivingEntity类了所以实际上这个句子只会更改命名，后面就可以用entity来指代toolAttackContext.getLivingTarget()
            LivingEntity entity = context.getLivingTarget();
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            if (randomNum <= 2){  entity.hurt(DamageSource.MAGIC,3 * modifier.getLevel());
                entity.invulnerableTime = 0;
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100  ,  modifier.getLevel()-1));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 ,  modifier.getLevel()-1));
            ModifierUtil.dropItem(entity, new ItemStack(Items.MUSHROOM_STEW));}
            entity.setLastHurtByMob(context.getAttacker());
        }
    }
}