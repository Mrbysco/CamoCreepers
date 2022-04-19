package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;

public class CamoRegistry {

	private static int ID = 0;

	public static void register() {
		registerEntity("camo_creeper", CamoCreeperEntity.class, "CamoCreeper", 80, 3, true,
				894731, 0);
	}

	public static void registerEntity(String registryName, Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		EntityRegistry.registerModEntity(new ResourceLocation(CamoCreepers.MOD_ID, registryName), entityClass, CamoCreepers.MOD_PREFIX + entityName, ID,
				CamoCreepers.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
		ID++;
	}

	public static void registerBiomes() {
		for (Biome biome : Biome.REGISTRY) {
			for (Biome.SpawnListEntry entry : new ArrayList<>(biome.getSpawnableList(EnumCreatureType.MONSTER))) {
				if (entry.entityClass == EntityCreeper.class) {
					EntityRegistry.addSpawn(CamoCreeperEntity.class, CamoConfig.general.camoCreeperWeight, CamoConfig.general.camoCreeperMin,
							CamoConfig.general.camoCreeperMax, EnumCreatureType.MONSTER, biome);
				}
			}
			if (CamoConfig.general.overrideCreeperSpawns) {
				biome.getSpawnableList(EnumCreatureType.MONSTER).forEach(entry -> {
					if (entry.entityClass == EntityCreeper.class) {
						EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.MONSTER, biome);
					}
				});
			}
		}
	}
}
