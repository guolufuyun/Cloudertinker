package com.fuyun.cloudertinker.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import slimeknights.tconstruct.smeltery.block.entity.controller.AlloyerBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.module.FuelModule;
import slimeknights.tconstruct.smeltery.block.entity.module.alloying.MixerAlloyTank;
import slimeknights.tconstruct.smeltery.block.entity.module.alloying.SingleAlloyingModule;

@Mixin(value = AlloyerBlockEntity.class, remap = false)
public interface AlloyerBlockEntityAccessor {
    @Accessor("tick")
    int getTick();
    @Accessor("tankHolder")
    LazyOptional<IFluidHandler> getTankHolder();

    @Accessor("tick")
    void setTick(int value);

    @Accessor("fuelModule")
    FuelModule getFuelModule();

    @Accessor("alloyTank")
    MixerAlloyTank getAlloyTank();

    @Accessor("alloyingModule")
    SingleAlloyingModule getAlloyingModule();

    @Invoker("tick")
    void invokeTick(Level level, BlockPos pos, BlockState state);

    @Invoker("isFormed")
    boolean invokeIsFormed();
}