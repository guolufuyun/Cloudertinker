package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.item.BottleFoodItem;
import com.fuyun.cloudertinker.item.Chimera_ingot;
import com.fuyun.cloudertinker.item.Clean_pastry;
import com.fuyun.cloudertinker.item.IronCooikeItem;
import com.fuyun.cloudertinker.tool.toolDefinitions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import static com.fuyun.cloudertinker.Cloudertinker.MOD_ID;



public class CloudertinkerItem {
    public CloudertinkerItem(){}
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    //物品锭什么的注册照着复制就行，如果有单独类改new Item的Item字段为你的类名，tab为创造模式显示栏，在哪个栏位显示
    public static final RegistryObject<Item> chimera_ingot = ITEMS.register("chimera_ingot", () -> new Chimera_ingot(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> colairon_ingot = ITEMS.register("colairon_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));


    //    public static final RegistryObject<Item> laomo = ITEMS.register("laomo", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> prublaze_ingot = ITEMS.register("prublaze_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> dectird_ingot = ITEMS.register("dectird_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> bloodshed_ingot = ITEMS.register("bloodshed_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> evilmare_ingot = ITEMS.register("evilmare_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> minogold_ingot = ITEMS.register("minogold_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> minodiamond_gem = ITEMS.register("minodiamond_gem", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> hydra_scale = ITEMS.register("hydra_scale", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> phantom_ingot = ITEMS.register("phantom_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> fierdistear = ITEMS.register("fierdistear", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> forest_gem = ITEMS.register("forest_gem", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> timering = ITEMS.register("timering", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> orescore = ITEMS.register("orescore", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> changeheart = ITEMS.register("changeheart", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> sorteye = ITEMS.register("sorteye", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> mazeslime_ingot = ITEMS.register("mazeslime_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> steeleafalloy = ITEMS.register("steeleafalloy", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> steelbone = ITEMS.register("steelbone", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> forestbone = ITEMS.register("forestbone", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> devil_chain = ITEMS.register("devil_chain", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> glaze_ingot = ITEMS.register("glaze_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> magnet_ingot = ITEMS.register("magnet_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> compositesteeleaf_ingot = ITEMS.register("compositesteeleaf_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> frostiron_ingot = ITEMS.register("frostiron_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> magala_ingot = ITEMS.register("magala_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<Item> glavenus_ingot = ITEMS.register("glavenus_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));


    public static final RegistryObject<Item> questiron_ingot = ITEMS.register("questiron_ingot", () -> new Item(new Item.Properties().tab(CloudertinkerTab.MATERIALS)));
    public static final RegistryObject<BlockItem> questiron_block = ITEMS.register("questiron_block", () -> new BlockItem(CloudertinkerBlock.questiron_block.get(), new Item.Properties().tab(CloudertinkerTab.MATERIALS)));



    public static final RegistryObject<Item> cola_bottle = ITEMS.register("cola_bottle", () -> new BottleFoodItem( new Item.Properties().stacksTo(16).tab(CloudertinkerTab.OTHERS).food(CloudertinkerFood.cola_bottle).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> iron_cookie = ITEMS.register("iron_cookie", () -> new IronCooikeItem( new Item.Properties().tab(CloudertinkerTab.OTHERS).food(CloudertinkerFood.iron_cookie)));
    public static final RegistryObject<Item> obsidian_cookie = ITEMS.register("obsidian_cookie", () -> new SwordItem(Modtires.obsidian_cookie,5,-2.4F,new Item.Properties().tab(CloudertinkerTab.OTHERS).food(CloudertinkerFood.obsidian_cookie)));
    public static final RegistryObject<Item> clean_pastry = ITEMS.register("clean_pastry", () -> new Clean_pastry( new Item.Properties().stacksTo(64).tab(CloudertinkerTab.OTHERS).food(CloudertinkerFood.clean_pastry)));
    //    杂物
    public static final RegistryObject<Item> fortification_scepter_core = ITEMS.register("fortification_scepter_core", () -> new Item( new Item.Properties().tab(CloudertinkerTab.OTHERS)));
    public static final RegistryObject<Item> zombie_scepter_core = ITEMS.register("zombie_scepter_core", () -> new Item( new Item.Properties().tab(CloudertinkerTab.OTHERS)));
    public static final RegistryObject<Item> lifedrain_scepter_core = ITEMS.register("lifedrain_scepter_core", () -> new Item( new Item.Properties().tab(CloudertinkerTab.OTHERS)));
    public static final RegistryObject<Item> twilight_scepter_core = ITEMS.register("twilight_scepter_core", () -> new Item( new Item.Properties().tab(CloudertinkerTab.OTHERS)));
    public static final RegistryObject<Item> bloodlust_power = ITEMS.register("bloodlust_power", () -> new Item( new Item.Properties().tab(CloudertinkerTab.OTHERS)));
    public static final RegistryObject<Item> phantom_card = ITEMS.register("phantom_card", () -> new Item( new Item.Properties().tab(CloudertinkerTab.OTHERS)));


    public static final ItemDeferredRegisterExtension OTHER_ITEM = new ItemDeferredRegisterExtension("cloudertinker");
    private static final Item.Properties CASTS = (new Item.Properties()).tab(CloudertinkerTab.TOOLANDPART).stacksTo(64);
    public static final CastItemObject giant_blade_cast = OTHER_ITEM.registerCast("giant_blade", CASTS);
    public static final RegistryObject<ToolPartItem> giant_blade= ITEMS.register("giant_blade", () -> new ToolPartItem(new Item.Properties().stacksTo(64).tab(CloudertinkerTab.TOOLANDPART), HeadMaterialStats.ID));
    public static final RegistryObject<ToolPartItem> knightmetal_ring_part= ITEMS.register("knightmetal_ring_part", () -> new ToolPartItem(new Item.Properties().stacksTo(64).tab(CloudertinkerTab.TOOLANDPART), StatlessMaterialStats.BINDING.getIdentifier()));
    public static final RegistryObject<ToolPartItem> hard_plating= ITEMS.register("hard_plating", () -> new ToolPartItem(new Item.Properties().stacksTo(64).tab(CloudertinkerTab.TOOLANDPART), PlatingMaterialStats.SHIELD.getId()));


    public static final RegistryObject<Item> giantsword = ITEMS.register("giantsword", () -> new ModifiableItem(new Item.Properties().stacksTo(1).tab(CloudertinkerTab.TOOLANDPART), toolDefinitions.GIANTSWORD));
    public static final RegistryObject<Item> hard_shield = ITEMS.register("hard_shield", () -> new ModifiableItem(new Item.Properties().stacksTo(1).tab(CloudertinkerTab.TOOLANDPART), toolDefinitions.HARD_SHIELD));



    public static void init(){

    }
}
