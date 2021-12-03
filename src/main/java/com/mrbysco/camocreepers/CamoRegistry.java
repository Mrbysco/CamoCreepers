package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class CamoRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CamoCreepers.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, CamoCreepers.MOD_ID);

	public static final RegistryObject<EntityType<CamoCreeperEntity>> CAMO_CREEPER = ENTITIES.register("camo_creeper", () ->
			register("camo_creeper", EntityType.Builder.<CamoCreeperEntity>of(CamoCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F).clientTrackingRange(8)));

	public static final RegistryObject<Item> CAMO_CREEPER_SPAWN_EGG = ITEMS.register("camo_creeper_spawn_egg" , () ->
			new ForgeSpawnEggItem(CAMO_CREEPER, 894731, 0, itemBuilder()));


	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(CAMO_CREEPER.get(), CamoCreeperEntity.createAttributes().build());
	}

	public static void addSpawn(BiomeLoadingEvent event) {
		Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
		if(biome != null) {
			MobSpawnSettings info = biome.getMobSettings();
			List<SpawnerData> spawns = event.getSpawns().getSpawner(MobCategory.MONSTER);
			for(SpawnerData entry : info.getMobs(MobCategory.MONSTER).unwrap()) {
				if(entry.type == EntityType.CREEPER) {
					spawns.add(new MobSpawnSettings.SpawnerData(CAMO_CREEPER.get(), Math.min(1, CamoConfig.COMMON.camoCreeperWeight.get()), CamoConfig.COMMON.camoCreeperMin.get(), CamoConfig.COMMON.camoCreeperMax.get()));
				}
			}
			if(CamoConfig.COMMON.overrideCreeperSpawns.get()) {
				info.getMobs(MobCategory.MONSTER).unwrap().removeIf((entry -> entry.type == EntityType.CREEPER));
			}
		}
	}

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	}
}
