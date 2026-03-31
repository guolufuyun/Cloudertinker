package com.fuyun.cloudertinker.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import slimeknights.tconstruct.smeltery.block.entity.controller.MelterBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.module.MeltingModuleInventory;
import slimeknights.tconstruct.smeltery.block.entity.module.SolidFuelModule;
import slimeknights.tconstruct.tables.block.entity.table.TinkerStationBlockEntity;

@Mixin(value = MelterBlockEntity.class,remap = false)
public interface MelterBlockEntityAccessor {
    // 访问私有 tick 字段
    @Accessor("tick")
    int getTick();
    @Accessor("tankHolder")
    LazyOptional<IFluidHandler> getTankHolder();

    @Accessor("inventoryHolder")
    LazyOptional<IItemHandler> getInventoryHolder();

    @Accessor("tick")
    void setTick(int value);

    // 访问私有 meltingInventory 字段
    @Accessor("meltingInventory")
    MeltingModuleInventory getMeltingInventory();

    @Invoker("tick")
    void invokeTick(Level level, BlockPos pos, BlockState state);

    // 调用私有 isFormed 方法
    @Invoker("isFormed")
    boolean invokeIsFormed();
    @Accessor("fuelModule")
    SolidFuelModule getFuelModule();
}
