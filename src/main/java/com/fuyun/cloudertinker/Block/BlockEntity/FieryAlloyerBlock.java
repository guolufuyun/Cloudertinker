package com.fuyun.cloudertinker.Block.BlockEntity;

import com.fuyun.cloudertinker.register.CloudertinkerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.mantle.util.BlockEntityHelper;
import slimeknights.tconstruct.smeltery.block.controller.AlloyerBlock;

public class FieryAlloyerBlock extends AlloyerBlock {

    public FieryAlloyerBlock(Properties props) {
        super(props);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter getter, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isFireSource(BlockState state, LevelReader level, BlockPos pos, Direction direction) {
        return true;
    }
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return 1; // 亮度等级 0-15
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FieryAlloyerBlockEntity(pos, state);
    }
    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return BlockEntityHelper.castTicker(type, CloudertinkerBlockEntity.fiery_alloyer_entity.get(), FieryAlloyerBlockEntity.SERVER_TICKER);
    }
}
