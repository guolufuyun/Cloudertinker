package com.fuyun.cloudertinker.Effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Armorbroken extends StaticEffect{
    protected Armorbroken(MobEffectCategory type, int color) {
        super(type, color);
    }
    public Armorbroken(){
        super(MobEffectCategory.HARMFUL, 0x828A9B);
        addAttributeModifier(Attributes.ARMOR, "fb86c5be-5c3d-5b26-f18c-9a644eb8adda", -0.125D, AttributeModifier.Operation.MULTIPLY_BASE);
    }

}
