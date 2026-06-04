package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Block.BlockEntity.FieryAlloyerBlockEntity;
import com.fuyun.cloudertinker.Block.BlockEntity.FieryMelterBlockEntity;
import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.BlockEntityTypeDeferredRegister;

public class CloudertinkerBlockEntity {
    public static final BlockEntityTypeDeferredRegister BLOCK_ENTITIES = new BlockEntityTypeDeferredRegister(Cloudertinker.MOD_ID);

    public static final RegistryObject<BlockEntityType<FieryAlloyerBlockEntity>> fiery_alloyer_entity  = BLOCK_ENTITIES.register("fiery_alloyer_entity", FieryAlloyerBlockEntity::new, CloudertinkerBlock.fiery_alloyer);
    public static final RegistryObject<BlockEntityType<FieryMelterBlockEntity>> fiery_melter_entity  = BLOCK_ENTITIES.register("fiery_melter_entity", FieryMelterBlockEntity::new, CloudertinkerBlock.fiery_melter);
}
