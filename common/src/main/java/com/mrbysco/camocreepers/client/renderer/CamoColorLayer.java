package com.mrbysco.camocreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.platform.Services;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.Optional;

public class CamoColorLayer extends RenderLayer<CreeperRenderState, CreeperModel> {
	private final ResourceLocation overlayLocation;

	public CamoColorLayer(RenderLayerParent<CreeperRenderState, CreeperModel> entityRendererIn, ResourceLocation overlay) {
		super(entityRendererIn);
		this.overlayLocation = overlay;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CreeperRenderState renderState, float yRot, float xRot) {
		if (renderState instanceof CamoCreeperRenderState camoRenderState && !renderState.isInvisible) {
			CreeperModel entityModel = this.getParentModel();

			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.overlayLocation));
			entityModel.setupAnim(camoRenderState);

			final Holder<Biome> biome = camoRenderState.biome;
			final Optional<ResourceKey<Biome>> optionalBiomeResourceKey = biome.unwrapKey();
			int baseColor = camoRenderState.baseColor;
			int color;
			if (optionalBiomeResourceKey.isPresent() && Services.PLATFORM.showNetherCamo() && biome.is(BiomeTags.IS_NETHER)) {
				final ResourceLocation location = optionalBiomeResourceKey.get().location();
				if (location != null && location.equals(Biomes.BASALT_DELTAS.location())) {
					color = 6052956;
				} else {
					color = 8733250;
				}
			} else if (Services.PLATFORM.showEndCamo() && biome.is(BiomeTags.IS_END)) {
				color = 15660724;
			} else if (biome.is(Constants.IS_MUSHROOM)) {
				color = 9138547;
			} else if (Services.PLATFORM.showCaveCamo() && camoRenderState.y < camoRenderState.seaLevel && !camoRenderState.canSeeSky) {
				color = 7631988;
			} else if (biome.is(Constants.IS_SANDY) || biome.is(BiomeTags.IS_BEACH)) {
				color = 14009494;
			} else {
				color = baseColor;
			}

			entityModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
					ARGB.multiply(-1, color));
		}
	}
}
