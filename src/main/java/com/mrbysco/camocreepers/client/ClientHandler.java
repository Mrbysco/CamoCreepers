package com.mrbysco.camocreepers.client;

import com.mrbysco.camocreepers.CamoRegistry;
import com.mrbysco.camocreepers.client.renderer.CamoCreeperRenderer;
import com.mrbysco.camocreepers.item.CustomSpawnEggItem;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(CamoRegistry.CAMO_CREEPER.get(), CamoCreeperRenderer::new);
	}

	public static void registerItemColors(final ColorHandlerEvent.Item event) {
		ItemColors colors = event.getItemColors();

		for(RegistryObject<Item> registryObject : CamoRegistry.ITEMS.getEntries()) {
			if(registryObject.get() instanceof CustomSpawnEggItem) {
				CustomSpawnEggItem spawnEgg = (CustomSpawnEggItem) registryObject.get();
				colors.register((stack, tintIndex) -> spawnEgg.getColor(tintIndex), spawnEgg);
			}
		}
	}
}
