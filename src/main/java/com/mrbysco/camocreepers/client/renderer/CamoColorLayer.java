package com.mrbysco.camocreepers.client.renderer;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class CamoColorLayer<T extends CamoCreeperEntity> implements LayerRenderer<T> {
	private final ResourceLocation overlayLocation;
	private final CamoCreeperRenderer camoRenderer;

	public CamoColorLayer(CamoCreeperRenderer renderer, ResourceLocation overlay) {
		this.camoRenderer = renderer;
		this.overlayLocation = overlay;
	}

	@Override
	public void doRenderLayer(T camoCreeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!camoCreeper.isInvisible()) {
			this.camoRenderer.bindTexture(overlayLocation);

			this.camoRenderer.getMainModel().setLivingAnimations(camoCreeper, limbSwing, limbSwingAmount, partialTicks);
			this.camoRenderer.getMainModel().setModelAttributes(this.camoRenderer.getMainModel());

			final World world = camoCreeper.getEntityWorld();
			final BlockPos pos = camoCreeper.getPosition();
			final Biome biome = world.getBiome(pos);
			int baseColor = biome.getGrassColorAtPos(pos);
			int color;
			if (world != null && pos != null) {
				if (CamoConfig.camo.netherCamo && BiomeDictionary.getTypes(biome).contains(Type.NETHER)) {
					color = 8733250;
				} else if (CamoConfig.camo.endCamo && BiomeDictionary.getTypes(biome).contains(Type.END)) {
					color = 15660724;
				} else if (BiomeDictionary.getTypes(biome).contains(Type.MUSHROOM)) {
					color = 9138547;
				} else if (CamoConfig.camo.caveCamo && !world.canSeeSky(pos)) {
					color = 7631988;
				} else if (BiomeDictionary.getTypes(biome).contains(Type.SANDY) || BiomeDictionary.getTypes(biome).contains(Type.BEACH)) {
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

			GlStateManager.color(r, g, b, 1.0F);
			this.camoRenderer.getMainModel().render(camoCreeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
