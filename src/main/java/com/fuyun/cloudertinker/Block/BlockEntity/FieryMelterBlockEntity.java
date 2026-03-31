package com.fuyun.cloudertinker.Block.BlockEntity;

import com.fuyun.cloudertinker.CTKConfig;
import com.fuyun.cloudertinker.mixins.FuelModuleAccessor;
import com.fuyun.cloudertinker.mixins.MelterBlockEntityAccessor;
import com.fuyun.cloudertinker.register.CloudertinkerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.smeltery.block.controller.ControllerBlock;
import slimeknights.tconstruct.smeltery.block.entity.component.TankBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.controller.MelterBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.module.MeltingModuleInventory;

public class FieryMelterBlockEntity extends MelterBlockEntity {
    private static final int INFINITE_TEMPERATURE = CTKConfig.COMMON.FIERY_TEMPERATURE.get();
    private static final int INFINITE_RATE = 2;
    public static final BlockEntityTicker<FieryMelterBlockEntity> SERVER_TICKER = (level, pos, state, entity) -> {
        entity.tick(level, pos, state);
    };

    public FieryMelterBlockEntity(BlockPos pos, BlockState state) {
        super(CloudertinkerBlock.fiery_melter_entity.get(), pos, state);
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.cloudertinker.fiery_melter");
    }
    public void tick(Level level, BlockPos pos, BlockState state) {
        MelterBlockEntityAccessor accessor = (MelterBlockEntityAccessor) this;
        BlockPos downPos = pos.below();
        BlockEntity downTE = level.getBlockEntity(downPos);

        if (downTE instanceof TankBlockEntity tankTE) {
            // 检查下方储罐是否有流体
            boolean hasFuelTank = tankTE.getTank().getFluidAmount() > 0;

            // Tick 0: 寻找燃料
            if (accessor.getTick() == 0) {
                if (!accessor.getFuelModule().hasFuel() &&
                        accessor.getMeltingInventory().canHeat(accessor.getFuelModule().findFuel(false))) {
                    accessor.getFuelModule().findFuel(true);
                }
            }

            // Tick 2: 加热和消耗燃料
            if (accessor.getTick() == 2) {
                boolean hasFuel = accessor.getFuelModule().hasFuel();
                if (hasFuel) {
                    // 使用高温高速加热
                    level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, true));
                    accessor.getMeltingInventory().heatItems(accessor.getFuelModule().getTemperature(), accessor.getFuelModule().getRate());
                    // 消耗燃料
                    accessor.getFuelModule().decreaseFuel(1);

                    // 如果燃料耗尽且下方储罐也没燃料，切换到独立模式
                    if (!accessor.getFuelModule().hasFuel() && !hasFuelTank) {
                        tickStandalone(level, pos, state, accessor);
                    }
                } else {
                    tickStandalone(level, pos, state, accessor);

                }
            }

            // 更新 tick 计数器
            accessor.setTick((accessor.getTick() + 1) % 4);
        } else {
            tickStandalone(level, pos, state, accessor);
        }
    }

    private void tickStandalone(Level level, BlockPos pos, BlockState state,
                                MelterBlockEntityAccessor accessor) {
        FuelModuleAccessor fuel = (FuelModuleAccessor) accessor.getFuelModule();
        if (accessor.getFuelModule().getTemperature()<INFINITE_TEMPERATURE||!accessor.getFuelModule().hasFuel()){fuel.setTemperature(INFINITE_TEMPERATURE);}
        MeltingModuleInventory inventory = accessor.getMeltingInventory();
        if (inventory != null) {
            if ( accessor.getMeltingInventory().canHeat(INFINITE_TEMPERATURE)){
                level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, true));

                inventory.heatItems(INFINITE_TEMPERATURE,INFINITE_RATE);
            }else {
                accessor.getMeltingInventory().coolItems();
                level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, false));
            }

        }
        accessor.setTick((accessor.getTick() + 1) % 4);
    }
}