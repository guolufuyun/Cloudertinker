package com.fuyun.cloudertinker.Block.BlockEntity;

import com.fuyun.cloudertinker.CTKConfig;
import com.fuyun.cloudertinker.mixins.AlloyerBlockEntityAccessor;
import com.fuyun.cloudertinker.mixins.FuelModuleAccessor;
import com.fuyun.cloudertinker.register.CloudertinkerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.smeltery.block.controller.ControllerBlock;
import slimeknights.tconstruct.smeltery.block.entity.component.TankBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.controller.AlloyerBlockEntity;

public class FieryAlloyerBlockEntity extends AlloyerBlockEntity {
    private static final int INFINITE_TEMPERATURE = CTKConfig.COMMON.FIERY_TEMPERATURE.get();
    private static final int INFINITE_RATE = 2;
    public static final BlockEntityTicker<FieryAlloyerBlockEntity> SERVER_TICKER = (level, pos, state, entity) -> {
        entity.tick(level, pos, state);
    };
    public FieryAlloyerBlockEntity(BlockPos pos, BlockState state) {
        super(CloudertinkerBlock.fiery_alloyer_entity.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.cloudertinker.fiery_alloyer");
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        AlloyerBlockEntityAccessor accessor = (AlloyerBlockEntityAccessor) this;
        BlockPos downPos = pos.below();
        BlockEntity downTE = level.getBlockEntity(downPos);

        if (downTE instanceof TankBlockEntity tankTE) {
            boolean hasFuelTank = tankTE.getTank().getFluidAmount() > 0;

            if (accessor.getTick() == 0) {
                int fuelTemp = accessor.getFuelModule().findFuel(false);
                accessor.getAlloyTank().setTemperature(fuelTemp > 0 ? fuelTemp : INFINITE_TEMPERATURE);
                if (!accessor.getFuelModule().hasFuel() && accessor.getAlloyingModule().canAlloy()) {
                    accessor.getFuelModule().findFuel(true);
                }
            }

            if (accessor.getTick() == 2) {
                boolean hasFuel = accessor.getFuelModule().hasFuel();

                if (hasFuel) {
                    level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, true));
                    accessor.getAlloyTank().setTemperature(accessor.getFuelModule().getTemperature());
                    accessor.getAlloyingModule().doAlloy();
                    accessor.getFuelModule().decreaseFuel(1);
                } else if (!hasFuelTank) {
                    tickStandalone(level, pos, state, accessor);
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, false));
                }
            }

            accessor.setTick((accessor.getTick() + 1) % 4);
        } else {
            if (accessor.getTick() == 0) {
                FuelModuleAccessor fuel = (FuelModuleAccessor) accessor.getFuelModule();
                fuel.setTemperature(INFINITE_TEMPERATURE);
                accessor.getAlloyTank().setTemperature(INFINITE_TEMPERATURE);
            }

            if (accessor.getTick() == 2) {
                if (accessor.getAlloyingModule().canAlloy()) {
                    accessor.getAlloyingModule().doAlloy();
                    level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, true));
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, false));
                }
            }

            accessor.setTick((accessor.getTick() + 1) % 4);

        }
    }

    private void tickStandalone(Level level, BlockPos pos, BlockState state,
                                AlloyerBlockEntityAccessor accessor) {
        FuelModuleAccessor fuel = (FuelModuleAccessor) accessor.getFuelModule();

        fuel.setTemperature(INFINITE_TEMPERATURE);
        accessor.getAlloyTank().setTemperature(INFINITE_TEMPERATURE);

        if (accessor.getAlloyingModule().canAlloy()) {
            accessor.getAlloyingModule().doAlloy();
            level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, true));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, false));
        }
    }
}
