package com.fuyun.cloudertinker.register;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class CloudertinkerFood {

    public static final FoodProperties iron_cookie = new FoodProperties.Builder().alwaysEat().nutrition(4).saturationMod(1.2F)
            .effect(() ->new MobEffectInstance(TinkerModifiers.bleeding.get(), 60, 2), 1.0F)
            .effect(() ->new MobEffectInstance(CloudertinkerEffects.Tosteel.get(), 1200, 1), 1.0F)
            .build();
    public static final FoodProperties obsidian_cookie = new FoodProperties.Builder().alwaysEat().nutrition(6).saturationMod(1.8F)
            .effect(() ->new MobEffectInstance(TinkerModifiers.bleeding.get(), 60, 5), 1.0F)
            .effect(() ->new MobEffectInstance(CloudertinkerEffects.Tosteel.get(), 2400, 4), 1.0F)
            .build();
    public static final FoodProperties cola_bottle = new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(3.8F)
            .effect(() ->new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 0), 1.0F).build();
    public static final FoodProperties clean_pastry = new FoodProperties.Builder().alwaysEat().fast().nutrition(3).saturationMod(2.4F).build();
}
