package com.fuyun.cloudertinker.Effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Tosteel extends StaticEffect{
    public Tosteel() {
        super(MobEffectCategory.BENEFICIAL, 0XD8D8D8);
        addAttributeModifier(Attributes.ARMOR, "d85e4596-00c3-4fba-888f-219d830a341b", +2, AttributeModifier.Operation.ADDITION);
    }
}
