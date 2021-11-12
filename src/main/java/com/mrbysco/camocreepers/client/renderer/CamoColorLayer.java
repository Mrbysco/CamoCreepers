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
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T camoCreeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(!camoCreeper.isInvisible()) {
			EntityModel<T> entityModel = this.getParentModel();
			entityModel.prepareMobModel(camoCreeper, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(entityModel);
			VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(this.overlayLocation));
			entityModel.setupAnim(camoCreeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

			final Level world = camoCreeper.getCommandSenderWorld();
			final BlockPos pos = camoCreeper.blockPosition();
			final Biome biome = world.getBiome(pos);
			int baseColor = BiomeColors.getAverageGrassColor(world, pos);
			int color;
			if(world != null && pos != null) {
				if(CamoConfig.COMMON.netherCamo.get() && biome.getBiomeCategory() == BiomeCategory.NETHER) {
					if(biome.getRegistryName() != null && biome.getRegistryName().equals(Biomes.BASALT_DELTAS.location())) {
						color = 6052956;
					} else {
						color = 8733250;
					}
				} else if(CamoConfig.COMMON.endCamo.get() && world.getBiome(pos).getBiomeCategory() == BiomeCategory.THEEND) {
					color = 15660724;
				} else if(world.getBiome(pos).getBiomeCategory() == BiomeCategory.MUSHROOM) {
					color = 9138547;
				} else if(CamoConfig.COMMON.caveCamo.get() && world.getBlockState(pos.above()).getBlock() == Blocks.CAVE_AIR) {
					color = 7631988;
				} else if(biome.getBiomeCategory() == BiomeCategory.DESERT || biome.getBiomeCategory() == BiomeCategory.BEACH) {
					color = 14009494;
				} else {
					color = baseColor;
				}
			} else {
				color = baseColor;
			}

			final float r = (float)(color >> 16 & 255) / 255.0F;
			final float g = (float)(color >> 8 & 255) / 255.0F;
			final float b = (float)(color & 255) / 255.0F;

			entityModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
		}
	}
}
