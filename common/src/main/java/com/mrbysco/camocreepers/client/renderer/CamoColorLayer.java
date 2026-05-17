package com.mrbysco.camocreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.platform.Services;
import net.minecraft.client.model.monster.creeper.CreeperModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class CamoColorLayer extends RenderLayer<CreeperRenderState, CreeperModel> {
	private final Identifier overlayLocation;

	public CamoColorLayer(RenderLayerParent<CreeperRenderState, CreeperModel> entityRendererIn, Identifier overlay) {
		super(entityRendererIn);
		this.overlayLocation = overlay;
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, CreeperRenderState renderState, float yRot, float xRot) {
		if (renderState instanceof CamoCreeperRenderState camoRenderState && !renderState.isInvisible) {
			CreeperModel entityModel = this.getParentModel();

			entityModel.setupAnim(camoRenderState);

			final Holder<Biome> biome = camoRenderState.biome;
			final Optional<ResourceKey<Biome>> optionalBiomeResourceKey = biome.unwrapKey();
			int baseColor = camoRenderState.baseColor;
			int color;
			if (optionalBiomeResourceKey.isPresent() && CamoConfig.COMMON.netherCamo.get() && biome.is(BiomeTags.IS_NETHER)) {
				final Identifier location = optionalBiomeResourceKey.get().identifier();
				if (location != null && location.equals(Biomes.BASALT_DELTAS.identifier())) {
					color = 6052956;
				} else {
					color = 8733250;
				}
			} else if (CamoConfig.COMMON.endCamo.get() && biome.is(BiomeTags.IS_END)) {
				color = 15660724;
			} else if (biome.is(Constants.IS_MUSHROOM)) {
				color = 9138547;
			} else if (CamoConfig.COMMON.caveCamo.get() && camoRenderState.y < camoRenderState.seaLevel && !camoRenderState.canSeeSky) {
				color = 7631988;
			} else if (biome.is(Constants.IS_SANDY) || biome.is(BiomeTags.IS_BEACH)) {
				color = 14009494;
			} else if (camoRenderState.feetState.is(BlockTags.LEAVES)) {
				color = camoRenderState.foliageColor;
			} else if (camoRenderState.feetState.is(Blocks.GRASS_BLOCK)) {
				color = baseColor;
			} else {
				if (camoRenderState.feetState.isSolid() && !camoRenderState.feetState.isAir()) {
					color = camoRenderState.feetState.getBlock().defaultMapColor().col;
				} else {
					color = baseColor;
				}
			}

			nodeCollector.order(1).submitModel(this.getParentModel(), renderState, poseStack,
					RenderTypes.armorCutoutNoCull(this.overlayLocation), packedLight, OverlayTexture.NO_OVERLAY,
					ARGB.multiply(-1, color), null, renderState.outlineColor, null);
		}
	}
}
