package com.fuyun.cloudertinker.rander;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.client.model.ChainModel;
import com.fuyun.cloudertinker.client.model.SpikeBlockModel;
import com.fuyun.cloudertinker.entities.ChainBlock;
import com.fuyun.cloudertinker.entities.ChainNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.armor.texture.ArmorTextureSupplier;
import slimeknights.tconstruct.library.client.armor.texture.TintedArmorTexture;
import slimeknights.tconstruct.library.client.materials.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.materials.MaterialRenderInfoLoader;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.utils.SimpleCache;

import java.util.List;
import java.util.Optional;

/**
 * 链锤投掷实体渲染器。
 *
 * <p>前半部分 [VanillaCopy] 自 {@code twilightforest.client.renderer.entity.BlockChainRenderer}（1.20 暮色森林）：
 * 链节不再使用 Forge PartEntity / getParts()，改为读取实体同步的 5 个链节锚点
 * {@link ChainBlock#getChainNodes()}，对每个锚点做「当前 tick / 上一 tick」线性插值后渲染。
 *
 * <p>贴图部分照抄匠魂钓竿浮漂 {@code slimeknights.tconstruct.tools.client.material.CombatFishingHookRenderer}：
 * 实体贴图不再写死，而是按「锤头部件的材质」选贴图 + 染色，规则与浮漂逐行一致：
 * <ol>
 *     <li>基础贴图 {@link #LOCAL} = {@code cloudertinker:chain_block/material}，
 *         真实文件 {@code assets/cloudertinker/textures/tinker_armor/chain_block/material.png}
 *         （必须放在 {@code textures/tinker_armor/} 下，因为匠魂的贴图校验器
 *         {@link ArmorTextureSupplier#TEXTURE_VALIDATOR} 只认该目录，浮漂也是这么放的）；</li>
 *     <li>材质变体贴图命名 = 基础路径 + 后缀，后缀优先取材质自身的贴图
 *         （{@code _命名空间_路径}），否则依次尝试材质的 fallback 关键字（{@code _metal} 等），
 *         存在的那个变体贴图用 {@code MaterialRenderInfo#vertexColor()} 染色；</li>
 *     <li>一个变体贴图都没有时，退回基础贴图并染色（所以「没生成过贴图」也不会花屏）。</li>
 * </ol>
 *
 * <p>变体贴图由匠魂的部件贴图生成器产出，登记表见
 * {@code assets/cloudertinker/tinkering/generator_part_textures.json} 里的
 * {@code cloudertinker:chain_block/material} 条目（stat_type = {@code tconstruct:head}）。
 *
 * <p>注意：{@link #TEXTURE_CACHE} 按材质缓存，资源包热重载后需要 {@link #clearCache()}；
 * 本渲染器在重载时不清（生成器产出新贴图本来就要重启游戏）。
 */
public class ChainBlockRenderer extends EntityRenderer<ChainBlock> {

	/** 基础贴图（不带材质后缀的那张）：cloudertinker:chain_block/material */
	private static final ResourceLocation LOCAL = Cloudertinker.getResource("chain_block/material");

	/** 带 textures/tinker_armor 前缀与 .png 后缀的完整路径。
	 *  匠魂的 {@code ArmorTextureSupplier#getTexturePath} 是包级私有，这里复制同样的规则。 */
	private static final ResourceLocation BASE = localTexturePath(LOCAL);

	/** 补全成完整贴图路径（= 匠魂 ArmorTextureSupplier#getTexturePath 的实现） */
	private static ResourceLocation localTexturePath(ResourceLocation name) {
		return name.withPath(ArmorTextureSupplier.FOLDER + '/' + name.getPath() + ".png");
	}

	/** 该后缀对应的变体贴图是否存在；存在则返回完整路径，否则 null */
	@Nullable
	private static ResourceLocation tryTexture(String suffix) {
		ResourceLocation texture = LOCAL.withSuffix(suffix);
		if (ArmorTextureSupplier.TEXTURE_VALIDATOR.test(texture)) {
			return localTexturePath(texture);
		}
		return null;
	}

	/** 每个材质 → 贴图 + 染色 + 发光。逻辑与浮漂的 TEXTURE_CACHE 一致。 */
	private static final SimpleCache<MaterialVariantId, MaterialTexture> TEXTURE_CACHE = new SimpleCache<>(material -> {
		Optional<MaterialRenderInfo> infoOptional = MaterialRenderInfoLoader.INSTANCE.getRenderInfo(material);
		int color = -1;
		int luminosity = 0;
		if (infoOptional.isPresent()) {
			MaterialRenderInfo info = infoOptional.get();
			// 先试「不染色」的材质贴图变体
			ResourceLocation untinted = info.texture();
			luminosity = info.luminosity();
			if (untinted != null) {
				ResourceLocation texture = tryTexture('_' + untinted.getNamespace() + '_' + untinted.getPath());
				if (texture != null) {
					return new MaterialTexture(texture, -1, luminosity);
				}
			}
			// 再试 fallback 关键字变体（_metal / _wood / _rock ...），这些用材质颜色染色
			color = info.vertexColor();
			for (String fallback : info.fallbacks()) {
				ResourceLocation texture = tryTexture('_' + fallback);
				if (texture != null) {
					return new MaterialTexture(texture, color, luminosity);
				}
			}
		}
		// 材质未知，或一个变体贴图都没生成过 → 基础贴图（未知材质时 color 为 -1 = 不改色）
		return new MaterialTexture(BASE, color, luminosity);
	});

	private final Model model;
	private final Model chainModel;

	public ChainBlockRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new SpikeBlockModel(context.bakeLayer(SpikeBlockModel.LAYER));
		this.chainModel = new ChainModel(context.bakeLayer(ChainModel.LAYER));
	}

	/** 清空材质贴图缓存（资源重载后调用） */
	public static void clearCache() {
		TEXTURE_CACHE.clear();
	}

	@Override
	public void render(ChainBlock chainBlock, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		super.render(chainBlock, yaw, partialTicks, stack, buffer, light);

		// 按锤头部件的材质选贴图与颜色（链节与锤头共用同一张贴图，因此一起变）
		MaterialTexture texture = TEXTURE_CACHE.apply(chainBlock.getMaterial());
		int packedLight = texture.applyLuminosity(light);

		stack.pushPose();
		VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(texture.texture()), false, chainBlock.isFoil());

		float pitch = Mth.lerp(partialTicks, chainBlock.xRotO, chainBlock.getXRot());
		stack.mulPose(Axis.YP.rotationDegrees(180 - Mth.wrapDegrees(yaw)));
		stack.mulPose(Axis.XP.rotationDegrees(pitch));

		stack.scale(-1.0F, -1.0F, 1.0F);
		this.model.renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
				texture.red(), texture.green(), texture.blue(), texture.alpha());
		stack.popPose();

		// 链节：手 -> 实体 的线性插值锚点（最多 5 个）
		List<ChainNode> nodes = chainBlock.getChainNodes();
		if (nodes != null) {
			for (ChainNode node : nodes) {
				if (node != null) {
					renderChain(chainBlock, node, yaw, partialTicks, stack, buffer, packedLight, texture, this.chainModel);
				}
			}
		}
	}

	/**
	 * 渲染单个链节。位置 = 链节锚点(插值) - 实体自身(插值)，即相对实体的偏移。
	 * 贴图与颜色沿用锤头材质（链条与锤头共用同一张图）。
	 */
	public static void renderChain(ChainBlock parent, ChainNode node, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light, MaterialTexture texture, Model chainModel) {
		double chainInX = Mth.lerp(partialTicks, node.xOld, node.getX()) - Mth.lerp(partialTicks, parent.xOld, parent.getX());
		double chainInY = Mth.lerp(partialTicks, node.yOld, node.getY()) - Mth.lerp(partialTicks, parent.yOld, parent.getY());
		double chainInZ = Mth.lerp(partialTicks, node.zOld, node.getZ()) - Mth.lerp(partialTicks, parent.zOld, parent.getZ());

		stack.pushPose();
		VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, chainModel.renderType(texture.texture()), false, parent.isFoil());

		stack.translate(chainInX, chainInY, chainInZ);
		float pitch = Mth.lerp(partialTicks, parent.xRotO, parent.getXRot());
		stack.mulPose(Axis.YP.rotationDegrees(180 - Mth.wrapDegrees(yaw)));
		stack.mulPose(Axis.XP.rotationDegrees(pitch));

		stack.scale(-1.0F, -1.0F, 1.0F);
		chainModel.renderToBuffer(stack, vertexConsumer, light, OverlayTexture.NO_OVERLAY,
				texture.red(), texture.green(), texture.blue(), texture.alpha());
		stack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(ChainBlock entity) {
		return TEXTURE_CACHE.apply(entity.getMaterial()).texture();
	}

	/**
	 * 材质 → 贴图 + 颜色 + 发光。等价于浮漂渲染器里的同名内部类，只是颜色改成
	 * {@code renderToBuffer} 需要的 0~1 浮点。
	 */
	public record MaterialTexture(ResourceLocation texture, int luminosity, float red, float green, float blue, float alpha) {
		/** color 为 ARGB（-1 = 不改色，全白不透明） */
		public MaterialTexture(ResourceLocation texture, int color, int luminosity) {
			this(texture, luminosity,
					(color >> 16 & 255) / 255F,
					(color >> 8 & 255) / 255F,
					(color & 255) / 255F,
					(color >> 24 & 255) / 255F);
		}

		/** 按材质发光等级提升光照（自发光材质会亮） */
		public int applyLuminosity(int packedLight) {
			if (luminosity > 0) {
				return TintedArmorTexture.applyLuminosity(packedLight, luminosity);
			}
			return packedLight;
		}
	}
}
