package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class Modtires {
//这里是等级，也算是构建了个挖掘等级，就像是钻石级工具啥的一样
    public static final Tier obsidian_cookie = TierSortingRegistry.registerTier(
            new ForgeTier(3, 250, 8.0F, 3, 22, BlockTags.create(Cloudertinker.prefix("needs_obsidian_cookie_tool")),
                    () -> Ingredient.of(CloudertinkerItem.obsidian_cookie.get())),
            Cloudertinker.prefix("obsidian_cookie"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
}
