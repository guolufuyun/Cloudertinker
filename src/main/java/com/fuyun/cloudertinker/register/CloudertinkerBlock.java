package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Block.BlockEntity.FieryMelterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.smeltery.block.controller.ControllerBlock;

import static com.fuyun.cloudertinker.Cloudertinker.MOD_ID;

public class CloudertinkerBlock {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
//    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
//            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cloudertinker.MOD_ID);

    public static final RegistryObject<Block> questiron_block = BLOCK.register("questiron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> fiery_melter = BLOCK.register("fiery_melter",
            () -> new FieryMelterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .lightLevel(state -> state.getValue(ControllerBlock.ACTIVE) ? 13 : 0)
                    .noOcclusion()));
//    public static final RegistryObject<BlockEntityType<FieryMelterBlockEntity>> fiery_melter_entity =
//            BLOCK_ENTITIES.register("fiery_melter_entity", () ->
//                    BlockEntityType.Builder.of(FieryMelterBlockEntity::new, fiery_melter.get()).build(null));

}
