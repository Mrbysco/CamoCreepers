package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeper;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.config.ModConfig;

import java.util.function.Predicate;

public class CamoCreepersFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, CamoConfig.commonSpec);

		CommonClass.init();

		registerSpawnPlacements();
		registerEntityAttributes();

		modifyBiomeSpawns();

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(entries ->
				entries.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get())
		);
	}

	private void registerSpawnPlacements() {
		SpawnPlacements.register(CamoRegistry.CAMO_CREEPER.get(), SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CamoCreeper::checkMonsterSpawnRules);
	}

	private void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(CamoRegistry.CAMO_CREEPER.get(), CamoCreeper.createAttributes().build());
	}

	private void modifyBiomeSpawns() {
		BiomeModifications.addSpawn(getContext(EntityType.CREEPER), MobCategory.MONSTER, CamoRegistry.CAMO_CREEPER.get(), 100, 4, 4);

		if (CamoConfig.COMMON.overrideCreeperSpawns.get()) {
			BiomeModifications.create(Constants.modLoc("remove_creepers"))
					.add(ModificationPhase.REMOVALS, getContext(EntityType.CREEPER), context ->
							context.getMobSpawnSettings().removeSpawnsOfEntityType(EntityType.CREEPER));
		}
	}

	private Predicate<BiomeSelectionContext> getContext(EntityType<?> type) {
		return BiomeSelectors.spawnsOneOf(type);
	}
}
