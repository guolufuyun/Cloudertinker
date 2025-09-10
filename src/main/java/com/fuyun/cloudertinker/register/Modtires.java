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

    public static final Tier obsidian_cookie = TierSortingRegistry.registerTier(
            new ForgeTier(3, 250, 8.0F, 3f, 22, BlockTags.create(Cloudertinker.prefix("needs_obsidian_cookie_tool")), () -> Ingredient.of(CloudertinkerItem.obsidian_cookie.get())),
            Cloudertinker.prefix("obsidian_cookie"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));

}
