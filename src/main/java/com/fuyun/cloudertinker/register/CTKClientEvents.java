package com.fuyun.cloudertinker.register;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import slimeknights.tconstruct.smeltery.client.render.MelterBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.TankBlockEntityRenderer;

@EventBusSubscriber(modid = "cloudertinker", value = Dist.CLIENT, bus = Bus.MOD)
public class CTKClientEvents {

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerBlockEntityRenderer(
                CloudertinkerBlockEntity.fiery_melter_entity.get(), MelterBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(CloudertinkerBlockEntity.fiery_alloyer_entity.get(), TankBlockEntityRenderer::new);
        }
    }

