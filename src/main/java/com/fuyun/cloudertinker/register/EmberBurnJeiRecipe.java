package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.item.EmberFeatherFan;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmberBurnJeiRecipe {

    public static final RecipeType<EmberBurnJeiRecipe> TYPE =
            RecipeType.create(
                    "cloudertinker",
                    "ember_burn",
                    EmberBurnJeiRecipe.class
            );

    public enum Kind {
        ITEM,
        BLOCK,
        ENTITY
    }

    private final Kind kind;

    private final ItemStack input;
    private final ItemStack output;

    private final EntityType<?> inputEntity;
    private final EntityType<?> outputEntity;

    private final ResourceLocation id;

    public EmberBurnJeiRecipe(
            Kind kind,
            ItemStack input,
            ItemStack output,
            ResourceLocation id
    ) {
        this.kind = kind;
        this.input = input;
        this.output = output;
        this.inputEntity = null;
        this.outputEntity = null;
        this.id = id;
    }

    public EmberBurnJeiRecipe(
            EntityType<?> inputEntity,
            EntityType<?> outputEntity,
            ResourceLocation id
    ) {
        this.kind = Kind.ENTITY;
        this.input = ItemStack.EMPTY;
        this.output = ItemStack.EMPTY;
        this.inputEntity = inputEntity;
        this.outputEntity = outputEntity;
        this.id = id;
    }

    public Kind getKind() {
        return kind;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public EntityType<?> getInputEntity() {
        return inputEntity;
    }

    public EntityType<?> getOutputEntity() {
        return outputEntity;
    }

    public ResourceLocation getId() {
        return id;
    }

    public static List<EmberBurnJeiRecipe> createAll() {
        List<EmberBurnJeiRecipe> recipes = new ArrayList<>();

        /*
         * =========================================================
         * 物品转换
         * =========================================================
         */
        for (Map.Entry<Item, Item> entry :
                EmberFeatherFan.EMBER_BURN_ITEM_MAP.entrySet()) {

            Item inputItem = entry.getKey();
            Item outputItem = entry.getValue();

            ResourceLocation inputId =
                    ForgeRegistries.ITEMS.getKey(inputItem);

            ResourceLocation outputId =
                    ForgeRegistries.ITEMS.getKey(outputItem);

            if (inputId == null || outputId == null) {
                continue;
            }

            recipes.add(
                    new EmberBurnJeiRecipe(
                            Kind.ITEM,
                            new ItemStack(inputItem),
                            new ItemStack(outputItem),
                            new ResourceLocation(
                                    "cloudertinker",
                                    "ember_burn/item/"
                                            + inputId.getNamespace()
                                            + "_"
                                            + inputId.getPath()
                                            + "_to_"
                                            + outputId.getNamespace()
                                            + "_"
                                            + outputId.getPath()
                            )
                    )
            );
        }

        /*
         * =========================================================
         * 方块转换
         *
         * JEI 显示成普通 ItemStack。
         *
         * 实际逻辑仍然是 Block -> Block。
         * =========================================================
         */
        for (Map.Entry<Block, Block> entry :
                EmberFeatherFan.EMBER_BURN_BLOCK_MAP.entrySet()) {

            Block inputBlock = entry.getKey();
            Block outputBlock = entry.getValue();

            if (inputBlock == null || outputBlock == null) {
                continue;
            }

            ItemStack inputStack = new ItemStack(inputBlock);
            ItemStack outputStack = new ItemStack(outputBlock);

            if (inputStack.isEmpty() || outputStack.isEmpty()) {
                continue;
            }

            ResourceLocation inputId =
                    ForgeRegistries.BLOCKS.getKey(inputBlock);

            ResourceLocation outputId =
                    ForgeRegistries.BLOCKS.getKey(outputBlock);

            if (inputId == null || outputId == null) {
                continue;
            }

            recipes.add(
                    new EmberBurnJeiRecipe(
                            Kind.BLOCK,
                            inputStack,
                            outputStack,
                            new ResourceLocation(
                                    "cloudertinker",
                                    "ember_burn/block/"
                                            + inputId.getNamespace()
                                            + "_"
                                            + inputId.getPath()
                                            + "_to_"
                                            + outputId.getNamespace()
                                            + "_"
                                            + outputId.getPath()
                            )
                    )
            );
        }

        /*
         * =========================================================
         * 生物转换
         * =========================================================
         */
        for (Map.Entry<EntityType<?>, EntityType<?>> entry :
                EmberFeatherFan.EMBER_BURN_ENTITY_MAP.entrySet()) {

            EntityType<?> inputEntity = entry.getKey();
            EntityType<?> outputEntity = entry.getValue();

            ResourceLocation inputId =
                    ForgeRegistries.ENTITY_TYPES.getKey(inputEntity);

            ResourceLocation outputId =
                    ForgeRegistries.ENTITY_TYPES.getKey(outputEntity);

            if (inputId == null || outputId == null) {
                continue;
            }

            recipes.add(
                    new EmberBurnJeiRecipe(
                            inputEntity,
                            outputEntity,
                            new ResourceLocation(
                                    "cloudertinker",
                                    "ember_burn/entity/"
                                            + inputId.getNamespace()
                                            + "_"
                                            + inputId.getPath()
                                            + "_to_"
                                            + outputId.getNamespace()
                                            + "_"
                                            + outputId.getPath()
                            )
                    )
            );
        }

        return recipes;
    }
}