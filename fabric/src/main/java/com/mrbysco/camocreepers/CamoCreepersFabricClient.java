package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.renderer.CamoCreeperRenderer;
import com.mrbysco.camocreepers.item.SupplierSpawnEggItem;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.FastColor;

public class CamoCreepersFabricClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(CamoRegistry.CAMO_CREEPER.get(), CamoCreeperRenderer::new);

		for (SupplierSpawnEggItem<?> registryObject : SupplierSpawnEggItem.getModEggs()) {
			ColorProviderRegistry.ITEM.register((stack, layer) -> FastColor.ARGB32.opaque(registryObject.getColor(layer)), registryObject);
		}
	}
}
