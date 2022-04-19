package com.mrbysco.camocreepers.client.renderer;

import com.mrbysco.camocreepers.CamoCreepers;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class CamoCreeperRenderer extends RenderCreeper {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation CAMO_CREEPER_TEXTURES = new ResourceLocation(CamoCreepers.MOD_ID, "textures/entity/camo_creeper.png");

	public CamoCreeperRenderer(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.addLayer(new CamoColorLayer(this, CAMO_CREEPER_TEXTURES));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCreeper entity) {
		return null;
	}

	public static class Factory implements IRenderFactory<CamoCreeperEntity> {
		@Override
		public Render<? super CamoCreeperEntity> createRenderFor(RenderManager manager) {
			return new CamoCreeperRenderer(manager);
		}
	}
}
