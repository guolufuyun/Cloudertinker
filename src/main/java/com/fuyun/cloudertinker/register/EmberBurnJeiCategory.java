package com.fuyun.cloudertinker.register;


import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.compat.jei.JEICompat;
import twilightforest.compat.jei.renderers.EntityRenderer;





public class EmberBurnJeiCategory
            implements IRecipeCategory<EmberBurnJeiRecipe> {

        /*
         * ============================================================
         * JEI 分类尺寸
         * ============================================================
         */
        public static final int WIDTH = 116;
        public static final int HEIGHT = 54;

        /*
         * ============================================================
         * JEI 绘制对象
         * ============================================================
         */
        private final IDrawable background;
        private final IDrawable icon;
        private final IDrawable arrow;
        private final IDrawable slotBackground;

        /*
         * ============================================================
         * Twilight Forest 实体渲染器
         * ============================================================
         */
        private final EntityRenderer entityRenderer;

        public EmberBurnJeiCategory(IGuiHelper guiHelper) {

            /*
             * ========================================================
             * 1. 我们自己的 JEI 背景
             *
             * 不使用 Twilight Forest 的背景。
             * ========================================================
             */
            this.background = guiHelper.createBlankDrawable(
                    WIDTH,
                    HEIGHT
            );

            /*
             * ========================================================
             * 2. 我们自己的分类图标
             *
             * 使用余烬羽扇。
             * ========================================================
             */
            this.icon = guiHelper.createDrawableIngredient(
                    VanillaTypes.ITEM_STACK,
                    CloudertinkerItem.ember_feather_fan
                            .get()
                            .getDefaultInstance()
            );

            /*
             * ========================================================
             * 3. 只借用 Twilight Forest 的箭头
             *
             * 注意：
             *
             * 这里并没有使用暮色森林的背景。
             *
             * transformation_jei.png 只是作为箭头素材来源。
             *
             * Twilight Forest 1.19.2 的转换粉 JEI 中：
             *
             * UV：
             * 116, 0
             *
             * 尺寸：
             * 23 × 15
             * ========================================================
             */
            ResourceLocation transformationTexture =
                    TwilightForestMod.getGuiTexture(
                            "transformation_jei.png"
                    );

            this.arrow = guiHelper.createDrawable(
                    transformationTexture,
                    116,
                    0,
                    23,
                    15
            );

            /*
             * ========================================================
             * 4. 我们自己的 JEI 物品槽背景
             *
             * 仅用于 ItemStack / Block -> ItemStack / Block。
             *
             * 生物转换不会使用这个背景。
             * ========================================================
             */
            this.slotBackground = guiHelper.getSlotDrawable();

            /*
             * ========================================================
             * 5. 使用 Twilight Forest 自己的实体渲染器
             *
             * 注意：
             * 这里只借用实体渲染器。
             *
             * 不使用 Twilight Forest 的分类背景、
             * 物品槽或者其他 GUI。
             * ========================================================
             */
            this.entityRenderer = new EntityRenderer(32);
        }

        /*
         * ============================================================
         * JEI Recipe Type
         * ============================================================
         */
        @Override
        public RecipeType<EmberBurnJeiRecipe> getRecipeType() {
            return EmberBurnJeiRecipe.TYPE;
        }

        /*
         * ============================================================
         * 分类名称
         * ============================================================
         */
        @Override
        public Component getTitle() {
            return Component.literal("余烬转化");
        }

        /*
         * ============================================================
         * 分类背景
         * ============================================================
         */
        @Override
        public IDrawable getBackground() {
            return background;
        }

        /*
         * ============================================================
         * 分类图标
         * ============================================================
         */
        @Override
        public IDrawable getIcon() {
            return icon;
        }

        /*
         * ============================================================
         * 绘制箭头
         *
         * JEI 1.19.2 使用 PoseStack。
         *
         * 箭头位置：
         *
         * X = 46
         * Y = 19
         * ============================================================
         */
        @Override
        public void draw(
                EmberBurnJeiRecipe recipe,
                IRecipeSlotsView recipeSlotsView,
                PoseStack poseStack,
                double mouseX,
                double mouseY
        ) {

            arrow.draw(
                    poseStack,
                    46,
                    19
            );
        }

        /*
         * ============================================================
         * 设置 Recipe 内容
         * ============================================================
         */
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
             * 这里故意不设置 slotBackground。
             *
             * 因此不会出现：
             *
             * ┌──────┐
             * │ 生物 │
             * └──────┘
             *
             * 的物品槽方框。
             * ========================================================
             */
            if (recipe.getKind()
                    == EmberBurnJeiRecipe.Kind.ENTITY) {

                /*
                 * 输入生物
                 */
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

                /*
                 * 输出生物
                 */
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
             * 物品 / 方块转换
             *
             * ItemStack -> ItemStack
             *
             * 或：
             *
             * Block -> Block
             *
             * 由于 Block 最终在 JEI 中以 ItemStack 显示，
             * 因此这里统一使用 ItemStack。
             * ========================================================
             */

            /*
             * 输入物品
             */
            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            8,
                            19
                    )
                    .setBackground(
                            slotBackground,
                            -1,
                            -1
                    )
                    .addItemStack(
                            recipe.getInput()
                    );

            /*
             * 输出物品
             */
            builder.addSlot(
                            RecipeIngredientRole.OUTPUT,
                            76,
                            19
                    )
                    .setBackground(
                            slotBackground,
                            -1,
                            -1
                    )
                    .addItemStack(
                            recipe.getOutput()
                    );
        }
    }