package com.fuyun.cloudertinker.register;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.init.TFItems;

import static com.fuyun.cloudertinker.Cloudertinker.MOD_ID;

public class CloudertinkerPotion {
    public static String potion(String base) {
        return base + "_potion";
    }
    public static String longPotion(String base) {
        return potion(base) + "_long";
    }
    public static String strongPotion(String base) {
        return potion(base) + "_strong";
    }
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID);

    public static final RegistryObject<Potion> Armorbroken_POTION = POTIONS.register(potion("armorbroken"), () -> new Potion(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 200,0)));
    public static final RegistryObject<Potion> Long_Armorbroken_POTION = POTIONS.register(longPotion("armorbroken"), () -> new Potion(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 400,0)));
    public static final RegistryObject<Potion> Strong_Armorbroken_POTION = POTIONS.register(strongPotion("armorbroken"), () -> new Potion(new MobEffectInstance(CloudertinkerEffects.Armorbroken.get(), 200,1)));

    public static final RegistryObject<Potion> Bloodlust_POTION = POTIONS.register(potion("bloodlust"), () -> new Potion(new MobEffectInstance(CloudertinkerEffects.Bloodlust.get(), 2400,0)));
    public static  void init(){
        PotionBrewing.addMix(Potions.AWKWARD, TFItems.ARMOR_SHARD_CLUSTER.get(),CloudertinkerPotion.Armorbroken_POTION.get());
        PotionBrewing.addMix(CloudertinkerPotion.Armorbroken_POTION.get(), Items.REDSTONE,CloudertinkerPotion.Long_Armorbroken_POTION.get());
        PotionBrewing.addMix(CloudertinkerPotion.Armorbroken_POTION.get(), Items.GLOWSTONE_DUST,CloudertinkerPotion.Strong_Armorbroken_POTION.get());

        PotionBrewing.addMix(Potions.AWKWARD, CloudertinkerItem.bloodlust_power.get(),CloudertinkerPotion.Bloodlust_POTION.get());


    }
}
