package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeper;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Predicate;

public class CamoCreepersFabric implements ModInitializer {
	public static ConfigHolder<CamoConfig> config;

	@Override
	public void onInitialize() {
		config = AutoConfig.register(CamoConfig.class, JanksonConfigSerializer::new);

		CommonClass.init();

		registerSpawnPlacements();
		registerEntityAttributes();

		modifyBiomeSpawns();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries ->
				entries.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get())
		);
	}

	private void registerSpawnPlacements() {
		SpawnPlacements.register(CamoRegistry.CAMO_CREEPER.get(), SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CamoCreeper::checkMonsterSpawnRules);
	}

	private void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(CamoRegistry.CAMO_CREEPER.get(), CamoCreeper.createAttributes().build());
	}

	private void modifyBiomeSpawns() {
		BiomeModifications.addSpawn(getContext(EntityType.CREEPER), MobCategory.MONSTER, CamoRegistry.CAMO_CREEPER.get(), 100, 4, 4);

		if (config.get().general.overrideCreeperSpawns) {
			BiomeModifications.create(new ResourceLocation(Constants.MOD_ID, "remove_creepers"))
					.add(ModificationPhase.REMOVALS, getContext(EntityType.CREEPER), context -> {
						context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CREEPER);
					});
		}
	}

	private Predicate<BiomeSelectionContext> getContext(EntityType<?> type) {
		return BiomeSelectors.spawnsOneOf(type);
	}
}
