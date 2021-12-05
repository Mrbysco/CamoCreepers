package com.mrbysco.camocreepers.client;

import com.mrbysco.camocreepers.CamoRegistry;
import com.mrbysco.camocreepers.client.renderer.CamoCreeperRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(CamoRegistry.CAMO_CREEPER.get(), CamoCreeperRenderer::new);
	}
}
