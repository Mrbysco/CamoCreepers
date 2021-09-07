package com.mrbysco.camocreepers.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
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
			EntityModel<T> entityModel = this.getEntityModel();
			entityModel.setLivingAnimations(camoCreeper, limbSwing, limbSwingAmount, partialTicks);
			this.getEntityModel().copyModelAttributesTo(entityModel);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(this.overlayLocation));
			entityModel.setRotationAngles(camoCreeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

			final World world = camoCreeper.getEntityWorld();
			final BlockPos pos = camoCreeper.getPosition();
			final Biome biome = world.getBiome(pos);
			int color;
			if(world != null && pos != null) {
				if(biome.getCategory() == Category.NETHER) {
					if(biome.getRegistryName().equals(Biomes.BASALT_DELTAS.getLocation())) {
						color = 6052956;
					} else {
						color = 8733250;
					}
				} else if(world.getBiome(pos).getCategory() == Category.THEEND) {
					color = 15660724;
				} else if(world.getBiome(pos).getCategory() == Category.MUSHROOM) {
					color = 9138547;
				} else if(world.getBlockState(pos.up()).getBlock() == Blocks.CAVE_AIR) {
					color = 7631988;
				} else if(biome.getCategory() == Category.DESERT || biome.getCategory() == Category.BEACH) {
					color = 14009494;
				} else {
					color = BiomeColors.getGrassColor(world, pos);
				}
			} else {
				color = BiomeColors.getGrassColor(world, pos);
			}

			final float r = (float)(color >> 16 & 255) / 255.0F;
			final float g = (float)(color >> 8 & 255) / 255.0F;
			final float b = (float)(color & 255) / 255.0F;

			entityModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
		}
	}
}
