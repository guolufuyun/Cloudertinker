package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.plugin.jei.TConstructJEIConstants;
@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Cloudertinker.MODID,"jei");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
       registration.addRecipeCatalyst(new ItemStack(CloudertinkerItem.fiery_melter.get()), TConstructJEIConstants.MELTING);
    }
}
