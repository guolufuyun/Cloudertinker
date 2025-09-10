package com.fuyun.cloudertinker.register;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class CloudertinkerTab {

    //makeIcon里面那个Itemstack是创造模式物品栏图标对的物品
    //label里面的会影响zh_cn里面的本地化键名
    //如果你要开新类型像我这样往下派生就行



    public static final CreativeModeTab MATERIALS = new CreativeModeTab("cloudertinker.materials") {
        @Override
        public  ItemStack makeIcon() {
            return new ItemStack(CloudertinkerItem.phantom_ingot.get());
        }
    };



    public static final CreativeModeTab OTHERS = new CreativeModeTab("cloudertinker.others") {
        @Override
        public  ItemStack makeIcon() {
            return new ItemStack(CloudertinkerItem.obsidian_cookie.get());
        }
    };

    public static final CreativeModeTab TOOLANDPART = new CreativeModeTab("cloudertinker.toolandpart") {
        @Override
        public  ItemStack makeIcon() {
            return new ItemStack(CloudertinkerItem.knightmetal_ring_part.get());
        }
    };


    public CloudertinkerTab(){}
}
