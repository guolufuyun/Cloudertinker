package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.register.CloudertinkerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.client.render.TankBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.TankInventoryBlockEntityRenderer;

@EventBusSubscriber(modid = "cloudertinker", value = Dist.CLIENT, bus = Bus.MOD)
public class CTKClientEvents {

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerBlockEntityRenderer(
                CloudertinkerBlockEntity.fiery_melter_entity.get(), context -> new TankInventoryBlockEntityRenderer<>(BlockStateProperties.HORIZONTAL_FACING));
        event.registerBlockEntityRenderer(CloudertinkerBlockEntity.fiery_alloyer_entity.get(), TankBlockEntityRenderer::new);


    }
    }

