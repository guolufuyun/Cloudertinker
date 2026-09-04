package com.fuyun.cloudertinker.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

/**
 * 每个实体身上独立的蓝焰效果实例，携带该实体专属的武器攻击力数值。
 */
public class BlueBurnInstance extends MobEffectInstance {

    private float weaponAttack = 0f;

    public BlueBurnInstance(MobEffect effect, int duration, int amplifier) {
        super(effect, duration, amplifier);
    }

    public float getWeaponAttack() {
        return weaponAttack;
    }

    public void setWeaponAttack(float weaponAttack) {
        this.weaponAttack = weaponAttack;
    }
}
