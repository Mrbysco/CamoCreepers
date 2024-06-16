package com.mrbysco.camocreepers.client.renderer;

import com.mrbysco.camocreepers.Constants;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CamoCreeperRenderer extends CreeperRenderer {
	private static final ResourceLocation CAMO_CREEPER_TEXTURES = new ResourceLocation(Constants.MOD_ID, "textures/entity/camo_creeper.png");

	public CamoCreeperRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.addLayer(new CamoColorLayer(this, CAMO_CREEPER_TEXTURES));
	}
}
