package com.fuyun.cloudertinker.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import slimeknights.tconstruct.smeltery.block.entity.controller.MelterBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.module.FuelModule;
import slimeknights.tconstruct.smeltery.block.entity.module.MeltingModuleInventory;


@Mixin(value = MelterBlockEntity.class,remap = false)
public interface MelterBlockEntityAccessor {
    // 访问私有 tick 字段
    @Accessor("tick")
    int getTick();

    @Accessor("tick")
    void setTick(int value);

    // 访问私有 meltingInventory 字段
    @Accessor("meltingInventory")
    MeltingModuleInventory getMeltingInventory();

    // 调用私有 tick 方法
    @Invoker("tick")
    void invokeTick(Level level, BlockPos pos, BlockState state);

    @Invoker("isFormed")
    boolean invokeIsFormed();
    @Accessor("fuelModule")
    FuelModule getFuelModule();


}
