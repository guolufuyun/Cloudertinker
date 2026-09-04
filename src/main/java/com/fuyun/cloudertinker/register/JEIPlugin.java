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
            new ResourceLocation(Cloudertinker.MOD_ID, "jei");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    /*
     * ============================================================
     * 注册 JEI 分类
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
     * 注册余烬羽扇的所有转化配方
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
     * 注册 JEI 催化物
     * ============================================================
     */
    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {

        /*
         * 余烬羽扇
         *
         * 点击扇子时可以直接进入“余烬转化”
         */
        registration.addRecipeCatalyst(
                new ItemStack(
                        CloudertinkerItem.ember_feather_fan.get()
                ),
                EmberBurnJeiRecipe.TYPE
        );

        /*
         * 炽焰熔炉
         */
        registration.addRecipeCatalyst(
                new ItemStack(
                        CloudertinkerItem.fiery_melter.get()
                ),
                TConstructJEIConstants.MELTING
        );

        /*
         * 炽焰合金炉
         */
        registration.addRecipeCatalyst(
                new ItemStack(
                        CloudertinkerItem.fiery_alloyer.get()
                ),
                TConstructJEIConstants.ALLOY
        );
    }
}