package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

public class CloudertinkerTab {
//    物品栏的通用部分
    protected CloudertinkerTab() {
    }

    protected static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(Cloudertinker.MODID);
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS = SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cloudertinker.MODID);


    public static void initRegisters() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
    }






}

