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
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.helper.ModifierLootingHandler;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.IToolPart;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.library.utils.BlockSideHitListener;
import slimeknights.tconstruct.plugin.jei.partbuilder.PartBuilderCategory;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import javax.print.DocFlavor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CloudertinkerOther extends CloudertinkerTab{
//还是物品栏，杂项的
    public static final RegistryObject<CreativeModeTab> Cloudertinkerother = CREATIVE_TABS.register(
            "others", () -> CreativeModeTab.builder().title(Cloudertinker.makeTranslation("itemGroup", "others"))
                    .title(Component.translatable("itemGroup.cloudertinker.others"))
                    .icon(() -> new ItemStack(CloudertinkerItem.obsidian_cookie.get()))
                    .displayItems(CloudertinkerOther::addTabItems)
                    .build());

    public static Item.Properties material() {
        return new Item.Properties();
    }

    public static Item.Properties material(int stackSize) {
        return material().stacksTo(stackSize);
    }


    static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        Consumer<ItemStack> output1 = output::accept;
        output.accept(CloudertinkerItem.fortification_scepter_core.get());
        output.accept(CloudertinkerItem.lifedrain_scepter_core.get());
        output.accept(CloudertinkerItem.zombie_scepter_core.get());
        output.accept(CloudertinkerItem.twilight_scepter_core.get());
        output.accept(CloudertinkerItem.cola_bottle.get());
        output.accept(CloudertinkerItem.iron_cookie.get());
        output.accept(CloudertinkerItem.obsidian_cookie.get());
        output.accept(CloudertinkerItem.clean_pastry.get());
        output.accept(CloudertinkerItem.bloodlust_power.get());
        output.accept(CloudertinkerItem.phantom_card.get());
        output.accept(CloudertinkerItem.savage_tigermark_round.get());
        output.accept(CloudertinkerItem.tigermark_round.get());    }

}