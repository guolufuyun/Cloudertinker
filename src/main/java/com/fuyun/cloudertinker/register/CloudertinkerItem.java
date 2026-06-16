/*
 * @Author: w 3519533277@qq.com
 * @Date: 2026-06-14 19:58:42
 * @LastEditors: w 3519533277@qq.com
 * @LastEditTime: 2026-06-15 17:15:34
 * @FilePath: \Cloudertinker\src\main\java\com\fuyun\cloudertinker\register\CloudertinkerItem.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.item.*;
import com.fuyun.cloudertinker.item.Blockitem.FieryMelter;
import com.fuyun.cloudertinker.item.Blockitem.FieryAlloyer;
import com.fuyun.cloudertinker.item.Rounds.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

import static com.fuyun.cloudertinker.Cloudertinker.MODID;



public class CloudertinkerItem {
    public CloudertinkerItem(){}
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> chimera_ingot = ITEMS.register("chimera_ingot", () -> new Chimera_ingot(new Item.Properties()));
//物品注册表，工具我写在工具物品栏里面了

    //物品锭什么的注册照着复制就行，如果有单独类改new Item的Item字段为你的类名
    public static final RegistryObject<Item> colairon_ingot = ITEMS.register("colairon_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> prublaze_ingot = ITEMS.register("prublaze_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> dectird_ingot = ITEMS.register("dectird_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> bloodshed_ingot = ITEMS.register("bloodshed_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> evilmare_ingot = ITEMS.register("evilmare_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> minogold_ingot = ITEMS.register("minogold_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> minodiamond_gem = ITEMS.register("minodiamond_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> hydra_scale = ITEMS.register("hydra_scale", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> phantom_ingot = ITEMS.register("phantom_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> fierdistear = ITEMS.register("fierdistear", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> forest_gem = ITEMS.register("forest_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> timering = ITEMS.register("timering", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> orescore = ITEMS.register("orescore", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> changeheart = ITEMS.register("changeheart", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> sorteye = ITEMS.register("sorteye", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> mazeslime_ingot = ITEMS.register("mazeslime_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> steeleafalloy = ITEMS.register("steeleafalloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> steelbone = ITEMS.register("steelbone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> forestbone = ITEMS.register("forestbone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> devil_chain = ITEMS.register("devil_chain", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> glaze_ingot = ITEMS.register("glaze_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> magnet_ingot = ITEMS.register("magnet_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> compositesteeleaf_ingot = ITEMS.register("compositesteeleaf_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> frostiron_ingot = ITEMS.register("frostiron_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> magala_ingot = ITEMS.register("magala_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> glavenus_ingot = ITEMS.register("glavenus_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> frostspikeslime_ingot = ITEMS.register("frostspikeslime_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> blue_iron_ingot = ITEMS.register("blue_iron_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> questiron_ingot = ITEMS.register("questiron_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<BlockItem> questiron_block = ITEMS.register("questiron_block", () -> new BlockItem(CloudertinkerBlock.questiron_block.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> fiery_melter = ITEMS.register("fiery_melter", () -> new FieryMelter(CloudertinkerBlock.fiery_melter.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> fiery_alloyer = ITEMS.register("fiery_alloyer", () -> new FieryAlloyer(CloudertinkerBlock.fiery_alloyer.get(), new Item.Properties()));



    public static final RegistryObject<Item> cola_bottle = ITEMS.register("cola_bottle", () -> new BottleFoodItem( new Item.Properties().stacksTo(16).food(CloudertinkerFood.cola_bottle).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> iron_cookie = ITEMS.register("iron_cookie", () -> new IronCooikeItem( new Item.Properties().food(CloudertinkerFood.iron_cookie)));
    public static final RegistryObject<Item> obsidian_cookie = ITEMS.register("obsidian_cookie", () -> new SwordItem(Modtires.obsidian_cookie,5,-2.4F,new Item.Properties().food(CloudertinkerFood.obsidian_cookie)));
    public static final RegistryObject<Item> clean_pastry = ITEMS.register("clean_pastry", () -> new Clean_pastry( new Item.Properties().stacksTo(64).food(CloudertinkerFood.clean_pastry)));
    public static final RegistryObject<Item> bloodlust_power = ITEMS.register("bloodlust_power", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> phantom_card = ITEMS.register("phantom_card", () -> new Item( new Item.Properties()));

    //    杂物
    public static final RegistryObject<Item> fortification_scepter_core = ITEMS.register("fortification_scepter_core", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> zombie_scepter_core = ITEMS.register("zombie_scepter_core", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> lifedrain_scepter_core = ITEMS.register("lifedrain_scepter_core", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> twilight_scepter_core = ITEMS.register("twilight_scepter_core", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> savage_tigermark_round = ITEMS.register("savage_tigermark_round", () -> new Savage_round( new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> tigermark_round = ITEMS.register("tigermark_round", () -> new Normal_round( new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> fiery_tigermark_round = ITEMS.register("fiery_tigermark_round", () -> new Fiery_round( new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> void_power_round = ITEMS.register("void_power_round", () -> new Void_power_round( new Item.Properties().stacksTo(16)));


    //方块类要这样,
//    public static final RegistryObject<BlockItem> Laomo_block = ITEMS.register("laomo_block", () -> new BlockItem(CloudertinkerBlock.Laomo_block.get(), new Item.Properties()));

    public static final ItemDeferredRegisterExtension OTHER_ITEM = new ItemDeferredRegisterExtension("cloudertinker");
    private static final Item.Properties CASTS = (new Item.Properties()).stacksTo(64);
    public static final CastItemObject giant_blade_cast = OTHER_ITEM.registerCast("giant_blade", CASTS);



    public static void init(){

    }
}
