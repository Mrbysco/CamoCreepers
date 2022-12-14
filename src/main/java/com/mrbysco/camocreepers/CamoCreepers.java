package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.ClientHandler;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CamoCreepers.MOD_ID)
public class CamoCreepers {
	public static final String MOD_ID = "camocreepers";
	public static final Logger LOGGER = LogManager.getLogger();

	public CamoCreepers() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CamoConfig.commonSpec);
		eventBus.register(CamoConfig.class);

		CamoRegistry.ITEMS.register(eventBus);
		CamoRegistry.ENTITY_TYPES.register(eventBus);
		CamoRegistry.BIOME_MODIFIER_SERIALIZERS.register(eventBus);

		eventBus.addListener(CamoRegistry::registerEntityAttributes);

		eventBus.addListener(this::setup);
		eventBus.addListener(this::addTabContents);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerEntityRenders);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		CamoRegistry.entityAttributes();
	}

	private void addTabContents(final CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG);
		}
	}
}
