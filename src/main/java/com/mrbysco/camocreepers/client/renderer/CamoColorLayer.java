package com.mrbysco.camocreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

public class CamoColorLayer<T extends CamoCreeperEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final ResourceLocation overlayLocation;

	public CamoColorLayer(RenderLayerParent<T, M> entityRendererIn, ResourceLocation overlay) {
		super(entityRendererIn);
		this.overlayLocation = overlay;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, T camoCreeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!camoCreeper.isInvisible()) {
			EntityModel<T> entityModel = this.getParentModel();
			entityModel.prepareMobModel(camoCreeper, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(entityModel);
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.overlayLocation));
			entityModel.setupAnim(camoCreeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

			final Level level = camoCreeper.getCommandSenderWorld();
			final BlockPos pos = camoCreeper.blockPosition();
			final Holder<Biome> biome = level.getBiome(pos);
			final ResourceLocation location = biome.unwrapKey().get().location();
			int baseColor = BiomeColors.getAverageGrassColor(level, pos);
			int color;
			if (level != null && pos != null) {
				if (CamoConfig.COMMON.netherCamo.get() && Biome.getBiomeCategory(biome) == BiomeCategory.NETHER) {
					if (location != null && location.equals(Biomes.BASALT_DELTAS.location())) {
						color = 6052956;
					} else {
						color = 8733250;
					}
				} else if (CamoConfig.COMMON.endCamo.get() && Biome.getBiomeCategory(biome) == BiomeCategory.THEEND) {
					color = 15660724;
				} else if (Biome.getBiomeCategory(biome) == BiomeCategory.MUSHROOM) {
					color = 9138547;
				} else if (CamoConfig.COMMON.caveCamo.get() && pos.getY() < level.getSeaLevel() && !level.canSeeSky(pos)) {
					color = 7631988;
				} else if (Biome.getBiomeCategory(biome) == BiomeCategory.DESERT || Biome.getBiomeCategory(biome) == BiomeCategory.BEACH) {
					color = 14009494;
				} else {
					color = baseColor;
				}
			} else {
				color = baseColor;
			}

			final float r = (float) (color >> 16 & 255) / 255.0F;
			final float g = (float) (color >> 8 & 255) / 255.0F;
			final float b = (float) (color & 255) / 255.0F;

			entityModel.renderToBuffer(poseStack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
		}
	}
}
