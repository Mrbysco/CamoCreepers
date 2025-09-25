package com.mrbysco.camocreepers.client.renderer;

import com.mrbysco.camocreepers.Constants;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class CamoCreeperRenderer extends CreeperRenderer {
	private static final ResourceLocation CAMO_CREEPER_TEXTURES = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/camo_creeper.png");

	public CamoCreeperRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.addLayer(new CamoColorLayer(this, CAMO_CREEPER_TEXTURES));
	}

	@Override
	public CreeperRenderState createRenderState() {
		return new CamoCreeperRenderState();
	}

	@Override
	public void extractRenderState(Creeper creeper, CreeperRenderState renderState, float partialTick) {
		super.extractRenderState(creeper, renderState, partialTick);
		if (renderState instanceof CamoCreeperRenderState camoRenderState) {
			camoRenderState.biome = creeper.level().getBiome(creeper.blockPosition());
			camoRenderState.baseColor = BiomeColors.getAverageGrassColor(creeper.level(), creeper.blockPosition());
			camoRenderState.seaLevel = creeper.level().getSeaLevel();
			camoRenderState.canSeeSky = creeper.level().canSeeSky(creeper.blockPosition());
		}
	}
}
