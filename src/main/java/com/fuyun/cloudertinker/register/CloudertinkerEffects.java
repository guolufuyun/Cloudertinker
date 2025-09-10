package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Effects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.fuyun.cloudertinker.Cloudertinker.MOD_ID;



public class CloudertinkerEffects  {


    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);

    //注册固定这个格式就行
//    public static final RegistryObject<MobEffect> HongWen = EFFECT.register("hongwen", HongWen::new);
    public static final RegistryObject<MobEffect> Evilmarecurse = EFFECT.register("evilmarecurse", Evilmarecurse::new);
    public static final RegistryObject<MobEffect> ShildCooldown = EFFECT.register("shildcooldown", ShildCooldown::new);
    public static final RegistryObject<MobEffect> Armorbroken = EFFECT.register("armorbroken", Armorbroken::new);
    public static final RegistryObject<MobEffect> ResentmentBrand = EFFECT.register("resentmentbrand", ResentmentBrand::new);
    public static final RegistryObject<MobEffect> Bloodlust = EFFECT.register("bloodlust", Bloodlust::new);
    public static final RegistryObject<MobEffect> Bloodlust_beat = EFFECT.register("bloodlust_beat", Bloodlust_beat::new);
    public static final RegistryObject<MobEffect> Bloodlust_erode = EFFECT.register("bloodlust_erode", Bloodlust_erode::new);
    public static final RegistryObject<MobEffect> Tosteel = EFFECT.register("tosteel", Tosteel::new);



}
