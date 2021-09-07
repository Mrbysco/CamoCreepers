package com.mrbysco.camocreepers.client.renderer;

import com.mrbysco.camocreepers.CamoCreepers;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CamoCreeperRenderer extends CreeperRenderer {
	private static final ResourceLocation CAMO_CREEPER_TEXTURES = new ResourceLocation(CamoCreepers.MOD_ID, "textures/entity/camo_creeper.png");
	public CamoCreeperRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
		this.addLayer(new CamoColorLayer(this, CAMO_CREEPER_TEXTURES));
	}
}
