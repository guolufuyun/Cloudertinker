package com.fuyun.cloudertinker.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.client.model.TFModelLayers;

public class QuestRamSkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public QuestRamSkullModel(EntityModelSet modelSet) {
        super();

        ModelLayerLocation layer = TFModelLayers.QUEST_RAM_TROPHY;

        this.root = modelSet.bakeLayer(layer);
        this.head = root.getChild("head");
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
        // 渲染 root（包含所有子节点），和 vanilla SkullModel 一致
//        poseStack.translate(0.0F, -0.25F, 0.0F);
        poseStack.scale(0.7F, 0.7F, 0.7F);
        this.head.render(poseStack, vertexConsumer,
                packedLight, packedOverlay, red, green, blue, alpha);
    }
}
