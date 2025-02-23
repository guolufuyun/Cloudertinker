package com.alkaid.tinkersforge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Tinkersforge.MODID)
public class Tinkersforge {
    public static final String MODID = "tinkersforge";
    public Tinkersforge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
