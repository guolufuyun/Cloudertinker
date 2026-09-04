package com.fuyun.cloudertinker.register;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

import net.minecraft.network.chat.Component;

import twilightforest.compat.jei.JEICompat;
import twilightforest.compat.jei.renderers.EntityRenderer;

public class EmberBurnJeiCategory
        implements IRecipeCategory<EmberBurnJeiRecipe> {

    public static final int WIDTH = 116;
    public static final int HEIGHT = 54;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable arrow;

    private final EntityRenderer entityRenderer;

    public EmberBurnJeiCategory(
            IGuiHelper guiHelper
    ) {

        /*
         * JEI 空白背景
         */
        this.background =
                guiHelper.createBlankDrawable(
                        WIDTH,
                        HEIGHT
                );

        /*
         * 分类图标：
         * 使用余烬羽扇
         */
        this.icon =
                guiHelper.createDrawableIngredient(
                        VanillaTypes.ITEM_STACK,
                        CloudertinkerItem.ember_feather_fan
                                .get()
                                .getDefaultInstance()
                );

        /*
         * 使用 JEI 原生箭头
         *
         * 不使用转换粉箭头
         */
        this.arrow =
                guiHelper.getRecipeArrow();

        /*
         * 直接使用 Twilight Forest 自己的实体渲染器。
         *
         * 这就是转换粉 JEI 使用的 renderer。
         */
        this.entityRenderer =
                new EntityRenderer(32);
    }

    @Override
    public RecipeType<EmberBurnJeiRecipe> getRecipeType() {
        return EmberBurnJeiRecipe.TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("余烬转化");
    }
    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(
            EmberBurnJeiRecipe recipe,
            IRecipeSlotsView recipeSlotsView,
            net.minecraft.client.gui.GuiGraphics guiGraphics,
            double mouseX,
            double mouseY
    ) {

        /*
         * 普通 JEI 箭头
         */
        arrow.draw(
                guiGraphics,
                46,
                19
        );
    }

    @Override
    public void setRecipe(
            IRecipeLayoutBuilder builder,
            EmberBurnJeiRecipe recipe,
            IFocusGroup focuses
    ) {

        /*
         * ========================================================
         * 生物转换
         *
         * EntityType -> EntityType
         *
         * 使用暮色森林自己的 EntityRenderer
         * ========================================================
         */
        if (recipe.getKind()
                == EmberBurnJeiRecipe.Kind.ENTITY) {

            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            8,
                            11
                    )
                    .setCustomRenderer(
                            JEICompat.ENTITY_TYPE,
                            entityRenderer
                    )
                    .addIngredient(
                            JEICompat.ENTITY_TYPE,
                            recipe.getInputEntity()
                    );

            builder.addSlot(
                            RecipeIngredientRole.OUTPUT,
                            76,
                            11
                    )
                    .setCustomRenderer(
                            JEICompat.ENTITY_TYPE,
                            entityRenderer
                    )
                    .addIngredient(
                            JEICompat.ENTITY_TYPE,
                            recipe.getOutputEntity()
                    );

            return;
        }

        /*
         * ========================================================
         * 物品 / 方块
         *
         * 普通 ItemStack
         * ========================================================
         */

        builder.addSlot(
                        RecipeIngredientRole.INPUT,
                        8,
                        19
                )
                .setStandardSlotBackground()
                .addItemStack(
                        recipe.getInput()
                );

        builder.addSlot(
                        RecipeIngredientRole.OUTPUT,
                        76,
                        19
                )
                .setOutputSlotBackground()
                .addItemStack(
                        recipe.getOutput()
                );
    }
}