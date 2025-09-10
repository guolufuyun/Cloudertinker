package com.fuyun.cloudertinker.Effects;

import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class ShildCooldown extends NoMilkEffect{
   public static final ResourceLocation shield_cooldown = Cloudertinker.getResource("shield_cooldown");
    public ShildCooldown() {
        super(MobEffectCategory.NEUTRAL, 0XFFD700,true);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ModDataNBT entitydata = ModDataNBT.readFromNBT(event.player.getPersistentData());
        if (entitydata.getInt(shield_cooldown)>0)entitydata.putInt(shield_cooldown, entitydata.getInt(shield_cooldown)-1);
    }
    }
