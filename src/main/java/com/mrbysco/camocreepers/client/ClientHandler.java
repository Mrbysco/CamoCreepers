package com.mrbysco.camocreepers.client;

import com.mrbysco.camocreepers.CamoRegistry;
import com.mrbysco.camocreepers.client.renderer.CamoCreeperRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(CamoRegistry.CAMO_CREEPER.get(), CamoCreeperRenderer::new);
	}
}
