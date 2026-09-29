package com.fuyun.cloudertinker.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

/**
 * [VanillaCopy] of twilightforest.client.model.entity.ChainModel (1.20 暮色森林)
 * 单个链节：2x2x2 立方体，UV (56,36)，贴图 64x64。
 */
public class ChainModel extends ListModel<Entity> {

	/** 模型层 id：cloudertinker:chain */
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation("cloudertinker", "chain"), "main");

	private final ModelPart chain;

	public ChainModel(ModelPart root) {
		this.chain = root.getChild("chain");
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition partRoot = mesh.getRoot();

		partRoot.addOrReplaceChild("chain", CubeListBuilder.create()
						.texOffs(56, 36)
						.addBox(-1F, -1F, -1F, 2, 2, 2),
				PartPose.ZERO);

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(chain);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}
