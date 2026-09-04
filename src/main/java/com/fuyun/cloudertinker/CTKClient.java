package com.fuyun.cloudertinker;

import com.fuyun.cloudertinker.client.QuestRamSkullModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.client.SlimeskullArmorModel;
import twilightforest.TwilightForestMod;

/**
 * 注册暮色森林材料在 TConstruct 黏液头颅中的客户端渲染。
 * <p>
 * registerHeadModel 只是把工厂函数存入静态 Map，模型是懒加载的（首次渲染时才 bake），
 * 所以用 static 初始化块即可，无需绑定任何事件。
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = Cloudertinker.MOD_ID)
public class CTKClient {

    public static final MaterialId QUEST_RAM_WOOL =
            new MaterialId("cloudertinker", "wool");

    static {
        SlimeskullArmorModel.registerHeadModel(
                QUEST_RAM_WOOL,
                QuestRamSkullModel::new,
                TwilightForestMod.prefix("textures/model/questram.png")
        );
//        SlimeskullArmorModel.registerHeadModel(
//                MaterialIds.knightmetal,
//                SkullModelHelper.FLUID_CANNON,                                    // ModelLayerLocation
//                TwilightForestMod.prefix("textures/model/questram.png")
//        );
    }
}
