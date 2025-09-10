package com.fuyun.cloudertinker;

import com.fuyun.cloudertinker.register.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import java.util.Locale;

@Mod(Cloudertinker.MOD_ID)
@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD
)

public class Cloudertinker {
    public static boolean enabled1 = ModList.get().isLoaded("twilightforest");
    public static final String MOD_ID = "cloudertinker"; //是你的模组名，需要英文
    public Cloudertinker() {
        //注册表之类的东西
        //如果你新稿了别的注册表记得这边填一下
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CloudertinkerItem.ITEMS.register(eventBus);
        CloudertinkerItem.OTHER_ITEM.register(eventBus);
        CloudertinkerModifiers.MODIFIERS.register(eventBus);
        CloudertinkerFluid.FLUIDS.register(eventBus);
        CloudertinkerBlock.BLOCK.register(eventBus);
        CloudertinkerEffects.EFFECT.register(eventBus);
        CloudertinkerPotion.POTIONS.register(eventBus);
        if (enabled1){
            CloudertinkerSummonModifiers.MODIFIERS.register(eventBus);
        }
    }
    //Resourcelocation
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("cloudertinker", id);
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        CloudertinkerPotion.init();
       }
    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }
    //生成键名用的
    public static String makeDescriptionId(String type, String name) {
        return type + ".cloudertinker." + name;
    }
}
