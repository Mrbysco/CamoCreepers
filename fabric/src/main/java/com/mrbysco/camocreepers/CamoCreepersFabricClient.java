package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.renderer.CamoCreeperRenderer;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class CamoCreepersFabricClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRenderers.register(CamoRegistry.CAMO_CREEPER.get(), CamoCreeperRenderer::new);
	}
}
