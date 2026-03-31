package com.fuyun.cloudertinker.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import slimeknights.tconstruct.smeltery.block.entity.module.FuelModule;

@Mixin(value = FuelModule.class, remap = false)
public interface FuelModuleAccessor {

    @Accessor("temperature")
    int getTemperature();

    @Accessor("temperature")
    void setTemperature(int value);
}