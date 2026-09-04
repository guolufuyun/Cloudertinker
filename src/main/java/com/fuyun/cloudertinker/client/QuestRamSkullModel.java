package com.fuyun.cloudertinker.client;

import com.fuyun.cloudertinker.Cloudertinker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.tileentity.QuestRamTrophyModel;

public class QuestRamSkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public QuestRamSkullModel(EntityModelSet modelSet) {
        super();

        ModelLayerLocation layer = JappaPackReloadListener.INSTANCE.isJappaPackLoaded()
                ? TFModelLayers.QUEST_RAM_TROPHY
                : TFModelLayers.NEW_QUEST_RAM_TROPHY;

        this.root = modelSet.bakeLayer(layer);
        this.head = this.root.getChild("head");
    }

    @Override
    public void setupAnim(float mouthAnimation, float yRot, float xRot) {
        this.head.yRot = yRot * ((float) Math.PI / 180F);
        this.head.xRot = xRot * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {

//        poseStack.translate(0.0F, -0.25F, 0.0F);
        poseStack.scale(0.7F, 0.7F, 0.7F);
        this.head.render(poseStack, vertexConsumer,
                packedLight, packedOverlay, red, green, blue, alpha);
    }
}
