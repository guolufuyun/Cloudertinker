package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.Random;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Resentment.resentment;

public class Chainspike extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null) {
            //转换。由于已经是LivingEntity类了所以实际上这个句子只会更改命名，后面就可以用entity来指代toolAttackContext.getLivingTarget()
            LivingEntity entity = context.getLivingTarget();
            Random random = new Random();
            ModDataNBT tooldata = tool.getPersistentData();
            int randomNum = random.nextInt(100) + 1;
            if (randomNum <= 10 + (tooldata.getInt(resentment) * 0.05)) {
                entity.hurt(DamageSource.WITHER.bypassArmor().bypassMagic().bypassEnchantments(), 5);
                entity.invulnerableTime = 0;
                entity.addEffect(new MobEffectInstance(CloudertinkerEffects.Evilmarecurse.get(), 100, 1));
                entity.setLastHurtByMob(context.getAttacker());
            }
        }

    }

}
