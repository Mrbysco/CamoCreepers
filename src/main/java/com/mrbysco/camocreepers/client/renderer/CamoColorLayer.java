package com.mrbysco.camocreepers.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.Biomes;

public class CamoColorLayer<T extends CamoCreeperEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
	private final ResourceLocation overlayLocation;

	public CamoColorLayer(IEntityRenderer<T, M> entityRendererIn, ResourceLocation overlay) {
		super(entityRendererIn);
		this.overlayLocation = overlay;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T camoCreeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(!camoCreeper.isInvisible()) {
			EntityModel<T> entityModel = this.getParentModel();
			entityModel.prepareMobModel(camoCreeper, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(entityModel);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(this.overlayLocation));
			entityModel.setupAnim(camoCreeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

			final World world = camoCreeper.getCommandSenderWorld();
			final BlockPos pos = camoCreeper.blockPosition();
			final Biome biome = world.getBiome(pos);
			int baseColor = BiomeColors.getAverageGrassColor(world, pos);
			int color;
			if(world != null && pos != null) {
				if(CamoConfig.COMMON.netherCamo.get() && biome.getBiomeCategory() == Category.NETHER) {
					if(biome.getRegistryName().equals(Biomes.BASALT_DELTAS.location())) {
						color = 6052956;
					} else {
						color = 8733250;
					}
				} else if(CamoConfig.COMMON.endCamo.get() && world.getBiome(pos).getBiomeCategory() == Category.THEEND) {
					color = 15660724;
				} else if(world.getBiome(pos).getBiomeCategory() == Category.MUSHROOM) {
					color = 9138547;
				} else if(CamoConfig.COMMON.caveCamo.get() && world.getBlockState(pos.above()).getBlock() == Blocks.CAVE_AIR) {
					color = 7631988;
				} else if(biome.getBiomeCategory() == Category.DESERT || biome.getBiomeCategory() == Category.BEACH) {
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
