package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import util.method.ModifierLevel;

public class SilhouetteDance extends NoLevelsModifier {
    public SilhouetteDance() {
        MinecraftForge.EVENT_BUS.addListener(this::onFruitTeleport);
        MinecraftForge.EVENT_BUS.addListener(this::onPearlTeleport);
    }
    public void onFruitTeleport(EntityTeleportEvent.ChorusFruit event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity != null && ModifierLevel.EquipHasModifierlevel(entity, this.getId())) {
            //TODO:添加buff
        }
    }
    public void onPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        ServerPlayer player = event.getPlayer();
        if (player != null && ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
            event.setAttackDamage(0.0f);
            //TODO:添加buff
        }
    }


}
