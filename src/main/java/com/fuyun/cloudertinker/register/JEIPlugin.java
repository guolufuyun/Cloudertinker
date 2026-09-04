package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import slimeknights.tconstruct.plugin.jei.TConstructJEIConstants;
@JeiPlugin
public class JEIPlugin implements IModPlugin {

    private static final ResourceLocation UID =
            new ResourceLocation(
                    Cloudertinker.MODID,
                    "jei"
            );

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    /*
     * ============================================================
     * JEI Category
     * ============================================================
     */
    @Override
    public void registerCategories(
            IRecipeCategoryRegistration registration
    ) {

        registration.addRecipeCategories(
                new EmberBurnJeiCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                )
        );
    }

    /*
     * ============================================================
     * JEI Recipes
     * ============================================================
     */
    @Override
    public void registerRecipes(
            IRecipeRegistration registration
    ) {

        registration.addRecipes(
                EmberBurnJeiRecipe.TYPE,
                EmberBurnJeiRecipe.createAll()
        );
    }

    /*
     * ============================================================
     * JEI Catalysts
     * ============================================================
     */
    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {

        /*
         * 余烬羽扇
         *
         * 点击/查看用途：
         * → 余烬转化
         */
        registration.addRecipeCatalyst(
                new ItemStack(
                        CloudertinkerItem.ember_feather_fan.get()
                ),
                EmberBurnJeiRecipe.TYPE
        );

        /*
         * 保留原 Cloud Tinker JEI
         *
         * 炽焰熔炼器
         */
        registration.addRecipeCatalyst(
                new ItemStack(
                        CloudertinkerItem.fiery_melter.get()
                ),
                TConstructJEIConstants.MELTING
        );

        /*
         * 炽焰合金器
         */
        registration.addRecipeCatalyst(
                new ItemStack(
                        CloudertinkerItem.fiery_alloyer.get()
                ),
                TConstructJEIConstants.ALLOY
        );
    }
}