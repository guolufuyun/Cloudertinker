package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.client.model.ChainModel;
import com.fuyun.cloudertinker.client.model.SpikeBlockModel;
import com.fuyun.cloudertinker.rander.ChainBlockRenderer;
import com.fuyun.cloudertinker.tool.BlockAndChain;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.smeltery.client.render.TankBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.TankInventoryBlockEntityRenderer;

@EventBusSubscriber(modid = "cloudertinker", value = Dist.CLIENT, bus = Bus.MOD)
public class CTKClientEvents {

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerBlockEntityRenderer(
                CloudertinkerBlockEntity.fiery_melter_entity.get(), context -> new TankInventoryBlockEntityRenderer<>(BlockStateProperties.HORIZONTAL_FACING));
        event.registerBlockEntityRenderer(CloudertinkerBlockEntity.fiery_alloyer_entity.get(), TankBlockEntityRenderer::new);

        // 暮色链锤投掷实体渲染器
        event.registerEntityRenderer(CloudertinkerEntity.chain_block.get(), ChainBlockRenderer::new);
    }

    @SubscribeEvent
    static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SpikeBlockModel.LAYER, SpikeBlockModel::create);
        event.registerLayerDefinition(ChainModel.LAYER, ChainModel::create);
    }

    /**
     * 客户端初始化：注册物品模型谓词 {@code cloudertinker:thrown}。
     *
     * <p>链锤被掷出时，UUID 由 {@link com.fuyun.cloudertinker.tool.BlockAndChain} 写入匠魂持久数据
     * （{@code cloudertinker:chain_entity}，字符串形式的 UUID）。谓词返回 1.0F 后，
     * models/item/block_and_chain.json 的 overrides 会切换到 block_and_chain_thrown 模型；
     * 链子收回（持久数据被清除）后自动切回手持模型。</p>
     */
    @SubscribeEvent
    static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
                CloudertinkerTools.block_and_chain.get(),
                new ResourceLocation("cloudertinker", "thrown"),
                (stack, level, entity, seed) -> BlockAndChain.getThrownUuid(stack) != null ? 1.0F : 0.0F));
    }
}
