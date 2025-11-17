package com.fuyun.cloudertinker.register;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.fuyun.cloudertinker.Effects.*;
import slimeknights.tconstruct.common.TinkerEffect;

import static com.fuyun.cloudertinker.Cloudertinker.MODID;
public class CloudertinkerEffects  {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

//    效果注册表
    public static final RegistryObject<MobEffect> Evilmarecurse = EFFECT.register("evilmarecurse", Evilmarecurse::new);
    public static final RegistryObject<MobEffect> ShildCooldown = EFFECT.register("shildcooldown", ShildCooldown::new);
    public static final RegistryObject<MobEffect> Armorbroken = EFFECT.register("armorbroken", Armorbroken::new);
    public static final RegistryObject<MobEffect> ResentmentBrand = EFFECT.register("resentmentbrand", ResentmentBrand::new);
    public static final RegistryObject<MobEffect> Bloodlust = EFFECT.register("bloodlust", Bloodlust::new);
    public static final RegistryObject<MobEffect> Bloodlust_beat = EFFECT.register("bloodlust_beat", Bloodlust_beat::new);
    public static final RegistryObject<MobEffect> Bloodlust_erode = EFFECT.register("bloodlust_erode", Bloodlust_erode::new);
    public static final RegistryObject<MobEffect> Tosteel = EFFECT.register("tosteel", Tosteel::new);
}
