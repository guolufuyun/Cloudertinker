package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Block.BlockEntity.FieryMelterBlock;
import com.fuyun.cloudertinker.Block.BlockEntity.FieryMelterBlockEntity;
import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.smeltery.block.controller.ControllerBlock;
import slimeknights.tconstruct.smeltery.block.controller.MelterBlock;

import java.util.function.Supplier;

import static com.fuyun.cloudertinker.Cloudertinker.MODID;

public class CloudertinkerBlock {
//    方块注册表
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cloudertinker.MODID);
    public static final RegistryObject<Block> questiron_block = BLOCK.register("questiron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    // ✅ 注册 Fiery Melter 方块（关键！）
    public static final RegistryObject<Block> fiery_melter = BLOCK.register("fiery_melter",
            () -> new FieryMelterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .lightLevel(state -> state.getValue(ControllerBlock.ACTIVE) ? 13 : 0)
                    .noOcclusion()));

    // ✅ 注册对应的方块实体类型（必须与方块一起注册）
    public static final RegistryObject<BlockEntityType<FieryMelterBlockEntity>> fiery_melter_entity =
            BLOCK_ENTITIES.register("fiery_melter_entity", () ->
                    BlockEntityType.Builder.of(FieryMelterBlockEntity::new, fiery_melter.get()).build(null));
}