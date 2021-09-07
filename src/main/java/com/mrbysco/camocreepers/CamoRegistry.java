package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import com.mrbysco.camocreepers.item.CustomSpawnEggItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class CamoRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CamoCreepers.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, CamoCreepers.MOD_ID);

	public static final RegistryObject<EntityType<CamoCreeperEntity>> CAMO_CREEPER = ENTITIES.register("camo_creeper", () ->
			register("camo_creeper", EntityType.Builder.<CamoCreeperEntity>create(CamoCreeperEntity::new, EntityClassification.MONSTER)
					.size(0.6F, 1.7F).trackingRange(8)));

	public static final RegistryObject<Item> CAMO_CREEPER_SPAWN_EGG = ITEMS.register("camo_creeper_spawn_egg" , () ->
			new CustomSpawnEggItem(() -> CAMO_CREEPER.get(), 894731, 0, itemBuilder()));


	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(CAMO_CREEPER.get(), CamoCreeperEntity.registerAttributes().create());
	}

	public static void addSpawn(BiomeLoadingEvent event) {
		Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
		if(biome != null) {
			MobSpawnInfo info = biome.getMobSpawnInfo();
			List<Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
			for(Spawners entry : info.getSpawners(EntityClassification.MONSTER)) {
				if(entry.type == EntityType.CREEPER) {
					spawns.add(new MobSpawnInfo.Spawners(CAMO_CREEPER.get(), Math.min(1, CamoConfig.COMMON.camoCreeperWeight.get()), CamoConfig.COMMON.camoCreeperMin.get(), CamoConfig.COMMON.camoCreeperMax.get()));
				}
			}
			if(CamoConfig.COMMON.overrideCreeperSpawns.get()) {
				info.getSpawners(EntityClassification.MONSTER).removeIf((entry -> entry.type == EntityType.CREEPER));
			}
		}
	}

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().group(ItemGroup.MISC);
	}
}
