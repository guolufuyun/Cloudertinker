package com.fuyun.cloudertinker;

import com.fuyun.cloudertinker.rander.HeaverockRenderer;
import com.fuyun.cloudertinker.register.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import twilightforest.client.renderer.entity.ThrownIceRenderer;
import twilightforest.init.TFEntities;

import java.util.Locale;

import static slimeknights.tconstruct.TConstruct.makeTranslationKey;

@Mod(Cloudertinker.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = Cloudertinker.MODID)
public class Cloudertinker {
//    主类，不在这里注册是没有用的
    public static boolean enabled1 = ModList.get().isLoaded("twilightforest");
    public static final String MODID = "cloudertinker";
    public Cloudertinker() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CloudertinkerItem.ITEMS.register(modEventBus);
        CloudertinkerItem.OTHER_ITEM.register(modEventBus);
        CloudertinkerModifiers.MODIFIERS.register(modEventBus);
        CloudertinkerFluid.FLUIDS.register(modEventBus);
        CloudertinkerBlock.BLOCK.register(modEventBus);
        CloudertinkerEffects.EFFECT.register(modEventBus);
        CloudertinkerEntity.ENTITIES.register(modEventBus);
        CloudertinkerPotion.POTIONS.register(modEventBus);
        if (enabled1){
            CloudertinkerSummonModifiers.MODIFIERS.register(modEventBus);
        }
        modEventBus.register(new CloudertinkerMaterials());
        modEventBus.register(new CloudertinkerOther());
        modEventBus.register(new CloudertinkerTools());
        CloudertinkerMaterials.initRegisters();

    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("cloudertinker", id);
    }
    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }
    //生成键名用的
    public static String makeDescriptionId(String type, String name) {
        return type + ".cloudertinker." + name;
    }
    public static MutableComponent makeTranslation(String base, String name) {
        return Component.translatable(makeTranslationKey(base, name));
    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(CloudertinkerEntity.heave_rock.get(), HeaverockRenderer::new);
    }
}
