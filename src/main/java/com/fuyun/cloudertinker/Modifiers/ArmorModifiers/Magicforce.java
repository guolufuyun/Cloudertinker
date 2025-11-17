package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import static com.fuyun.cloudertinker.Modifiers.ArmorModifiers.Highlandwatching.harder;


public class Magicforce extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        ModDataNBT tooldata = armor.getPersistentData();
        if (entity.getHealth()==entity.getMaxHealth()){
            return amount* 0.4f;
        }
        if (amount>=entity.getHealth()&& tooldata.getInt(harder)>0){
            if (tooldata.getInt(harder)>=amount){
                tooldata.putInt(harder, (int) (tooldata.getInt(harder)-amount));
                return 0;
            }else {
                float damage=amount-tooldata.getInt(harder);
                tooldata.putInt(harder,0);
                return damage;
            }
        }
        return amount;
    }
}
