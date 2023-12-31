package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.ClientHandler;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CamoCreepers.MOD_ID)
public class CamoCreepers {
	public static final String MOD_ID = "camocreepers";
	public static final Logger LOGGER = LogManager.getLogger();

	public CamoCreepers(IEventBus eventBus) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CamoConfig.commonSpec);
		eventBus.register(CamoConfig.class);

		CamoRegistry.ITEMS.register(eventBus);
		CamoRegistry.ENTITY_TYPES.register(eventBus);
		CamoRegistry.BIOME_MODIFIER_SERIALIZERS.register(eventBus);

		eventBus.addListener(CamoRegistry::registerSpawnPlacements);
		eventBus.addListener(CamoRegistry::registerEntityAttributes);

		eventBus.addListener(this::addTabContents);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			eventBus.addListener(ClientHandler::registerEntityRenders);
		}
	}

	private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG);
		}
	}
}
