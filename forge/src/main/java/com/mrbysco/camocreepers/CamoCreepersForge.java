package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.ClientHandler;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeper;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import com.mrbysco.camocreepers.registry.CamoModifiers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class CamoCreepersForge {

	public CamoCreepersForge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CamoConfig.commonSpec);
		eventBus.register(CamoConfig.class);

		CommonClass.init();

		CamoModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);

		eventBus.addListener(CamoCreepersForge::registerSpawnPlacements);
		eventBus.addListener(CamoCreepersForge::registerEntityAttributes);

		eventBus.addListener(this::addTabContents);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerEntityRenders);
		});
	}

	private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG);
		}
	}

	public static void registerEntityAttributes(SpawnPlacementRegisterEvent event) {
		event.register(CamoRegistry.CAMO_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
	}

	public static void registerSpawnPlacements(EntityAttributeCreationEvent event) {
		event.put(CamoRegistry.CAMO_CREEPER.get(), CamoCreeper.createAttributes().build());
	}
}