package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.tool.Giantsword;
import com.fuyun.cloudertinker.tool.toolDefinitions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CloudertinkerTools extends CloudertinkerOther{
//工具的物品栏，为了方便我的工具部件啥的注册也在这了
    public static final RegistryObject<CreativeModeTab> Cloudertinkertools = CREATIVE_TABS.register(
            "toolandpart", () -> CreativeModeTab.builder().title(Cloudertinker.makeTranslation("itemGroup", "toolandpart"))
                    .title(Component.translatable("itemGroup.cloudertinker.toolandpart"))
                    .icon(() -> new ItemStack(CloudertinkerTools.knightmetal_ring_part.get()))
                    .displayItems(CloudertinkerTools::addTabItems)
                    .build());

    public static Item.Properties material() {
        return new Item.Properties();
    }

    public static Item.Properties material(int stackSize) {
        return material().stacksTo(stackSize);
    }

    public static final ItemObject<ModifiableItem> hard_shield = ITEMS.register("hard_shield", () -> new Giantsword(new Item.Properties().stacksTo(1), toolDefinitions.HARD_SHIELD));
    public static final ItemObject<ModifiableItem> tiantuistar_blade = ITEMS.register("tiantuistar_blade", () -> new Giantsword(new Item.Properties().stacksTo(1), toolDefinitions.TIANTUISTAR_BLADE));
    public static final ItemObject<ModifiableItem> giantsword = ITEMS.register("giantsword", () -> new Giantsword(new Item.Properties().stacksTo(1), toolDefinitions.GIANTSWORD));
    public static final ItemObject<ToolPartItem> giant_blade= ITEMS.register("giant_blade", () -> new ToolPartItem(new Item.Properties().stacksTo(64), HeadMaterialStats.ID));
    public static final ItemObject<ToolPartItem> knightmetal_ring_part= ITEMS.register("knightmetal_ring_part", () -> new ToolPartItem(new Item.Properties().stacksTo(64), StatlessMaterialStats.BINDING.getIdentifier()));
    public static final ItemObject<ToolPartItem> hard_plating= ITEMS.register("hard_plating", () -> new ToolPartItem(new Item.Properties().stacksTo(64), PlatingMaterialStats.SHIELD.getId()));


    static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        Consumer<ItemStack> output1 = output::accept;

        output.accept(CloudertinkerItem.giant_blade_cast.get());
        acceptPart(output1,giant_blade);
        acceptPart(output1,knightmetal_ring_part);
        acceptPart(output1,hard_plating);
        acceptTool(output1, giantsword);
        acceptTool(output1, hard_shield);
        acceptTool(output1, tiantuistar_blade);
    }
    private static void acceptTool(Consumer<ItemStack> output, Supplier<? extends IModifiable> tool) {
        ToolBuildHandler.addVariants(output, tool.get(), "");
    }
    private static void acceptPart(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item) {
        item.get().addVariants(output, "");
    }
}
