package com.fuyun.cloudertinker;
import com.fuyun.cloudertinker.client.QuestRamSkullModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.client.SlimeskullArmorModel;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import twilightforest.TwilightForestMod;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = Cloudertinker.MODID)
public class CTKClient {

    public static final MaterialId QUESTING_RAM_MATERIAL =
            new MaterialId(TwilightForestMod.ID, "questing_ram");

    static {
        SlimeskullArmorModel.registerHeadModel(
                MaterialIds.wool,
                QuestRamSkullModel::new,
                TwilightForestMod.prefix("textures/model/questram.png")
        );
    }
}
