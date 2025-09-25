package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.ClientHandler;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeper;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import com.mrbysco.camocreepers.registry.CamoModifiers;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@Mod(Constants.MOD_ID)
public class CamoCreepersNeoForge {

	public CamoCreepersNeoForge(IEventBus eventBus, ModContainer container, Dist dist) {
		container.registerConfig(ModConfig.Type.COMMON, CamoConfig.commonSpec);
		eventBus.register(CamoConfig.class);

		CommonClass.init();

		CamoModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);

		eventBus.addListener(CamoCreepersNeoForge::registerSpawnPlacements);
		eventBus.addListener(CamoCreepersNeoForge::registerEntityAttributes);

		eventBus.addListener(this::addTabContents);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			eventBus.addListener(ClientHandler::registerEntityRenders);
		}
	}

	private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get());
		}
	}

	public static void registerEntityAttributes(RegisterSpawnPlacementsEvent event) {
		event.register(CamoRegistry.CAMO_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
	}

	public static void registerSpawnPlacements(EntityAttributeCreationEvent event) {
		event.put(CamoRegistry.CAMO_CREEPER.get(), CamoCreeper.createAttributes().build());
	}
}