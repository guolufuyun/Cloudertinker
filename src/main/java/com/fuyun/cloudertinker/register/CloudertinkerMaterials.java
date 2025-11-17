package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class CloudertinkerMaterials extends CloudertinkerTab{
//    这个是材料物品栏
    public static final RegistryObject<CreativeModeTab> Cloudertinkermaterials = CREATIVE_TABS.register(
            "materials", () -> CreativeModeTab.builder().title(Cloudertinker.makeTranslation("itemGroup", "materials"))
                    .title(Component.translatable("itemGroup.cloudertinker.materials"))
                    .icon(() -> new ItemStack(CloudertinkerItem.phantom_ingot.get()))
                    .displayItems(CloudertinkerMaterials::addTabItems)
                    .build());
        public static Item.Properties material() {
            return new Item.Properties();
        }

        public static Item.Properties material(int stackSize) {
            return material().stacksTo(stackSize);
        }

        static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
            output.accept(CloudertinkerItem.chimera_ingot.get());
            output.accept(CloudertinkerItem.colairon_ingot.get());
            output.accept(CloudertinkerItem.prublaze_ingot.get());
            output.accept(CloudertinkerItem.bloodshed_ingot.get());
            output.accept(CloudertinkerItem.evilmare_ingot.get());
            output.accept(CloudertinkerItem.dectird_ingot.get());
            output.accept(CloudertinkerItem.minogold_ingot.get());
            output.accept(CloudertinkerItem.minodiamond_gem.get());
            output.accept(CloudertinkerItem.phantom_ingot.get());
            output.accept(CloudertinkerItem.hydra_scale.get());
            output.accept(CloudertinkerItem.fierdistear.get());
            output.accept(CloudertinkerItem.forest_gem.get());
            output.accept(CloudertinkerItem.timering.get());
            output.accept(CloudertinkerItem.orescore.get());
            output.accept(CloudertinkerItem.changeheart.get());
            output.accept(CloudertinkerItem.sorteye.get());
            output.accept(CloudertinkerItem.mazeslime_ingot.get());
            output.accept(CloudertinkerItem.steeleafalloy.get());
            output.accept(CloudertinkerItem.steelbone.get());
            output.accept(CloudertinkerItem.forestbone.get());
            output.accept(CloudertinkerItem.devil_chain.get());
            output.accept(CloudertinkerItem.compositesteeleaf_ingot.get());
            output.accept(CloudertinkerItem.glaze_ingot.get());
            output.accept(CloudertinkerItem.magnet_ingot.get());
            output.accept(CloudertinkerItem.questiron_block.get());
            output.accept(CloudertinkerItem.questiron_ingot.get());
            output.accept(CloudertinkerItem.frostiron_ingot.get());
            output.accept(CloudertinkerItem.magala_ingot.get());
            output.accept(CloudertinkerItem.glavenus_ingot.get());
            output.accept(CloudertinkerItem.frostspikeslime_ingot.get());
        }
    }