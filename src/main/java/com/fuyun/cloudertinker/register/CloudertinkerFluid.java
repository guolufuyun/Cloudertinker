package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Cloudertinker;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;

import static com.fuyun.cloudertinker.Cloudertinker.MOD_ID;

public class CloudertinkerFluid{
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);
    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000).descriptionId(Cloudertinker.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FluidType.Properties cool(String name) {
        return cool().descriptionId(Cloudertinker.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FluidType.Properties cool() {
        return FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FlowingFluidObject<ForgeFlowingFluid> register(String name, int temp) {
        return FLUIDS.register(name).type(hot(name).temperature(temp).lightLevel(12)).block(Material.LAVA, 15).bucket().flowing();
    }

    //复制这行，只改<ForgeFlowingFluid>后的命名空间，和register里面的"xxxx"即可,temp一般用不上，瞎填就行
    //材质和model什么的在数据包(resourcepacks）部分
//    public static final FluidObject<ForgeFlowingFluid> molten_laomo = register("molten_laomo", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_fiery = register("molten_fiery", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_knightmetal = register("molten_knightmetal", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_evilmare = register("molten_evilmare", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_bloodshed = register("molten_bloodshed", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_prublaze = register("molten_prublaze", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_dectird = register("molten_dectird", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_minodiamond = register("molten_minodiamond", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_minogold = register("molten_minogold", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_phantom = register("molten_phantom", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_fierdistear = register("molten_fierdistear", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_forestgem = register("molten_forestgem", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_timering = register("molten_timering", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_orescore = register("molten_orescore", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_changeheart = register("molten_changeheart", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_sorteye = register("molten_sorteye", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_mazeslime = register("molten_mazeslime", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_castone = register("molten_castone", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_chimera = register("molten_chimera", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_glaze = register("molten_glaze", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_magnet = register("molten_magnet", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_questiron = register("molten_questiron", 5867);
    public static final FluidObject<ForgeFlowingFluid> fire_tear = register("fire_tear", 5867);
    public static final FluidObject<ForgeFlowingFluid> fire_blood = register("fire_blood", 5867);
    public static final FluidObject<ForgeFlowingFluid> frost_essence = register("frost_essence", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_frostiron = register("molten_frostiron", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_magala = register("molten_magala", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_glavenus = register("molten_glavenus", 5867);
    public static final FluidObject<ForgeFlowingFluid> molten_carminite = register("molten_carminite", 5867);
}
