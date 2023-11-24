package com.mrbysco.camocreepers.modifier;

import com.mojang.serialization.Codec;
import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

import java.util.List;

public record AddEntityToSameBiomesModifier(EntityType<?> originalType, EntityType<?> newType, int weight,
											int minGroup, int maxGroup) implements BiomeModifier {
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD) {
			MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
			MobSpawnSettings info = biome.value().getMobSettings();
			final List<SpawnerData> spawnsList = spawns.getSpawner(MobCategory.MONSTER);
			for (SpawnerData entry : info.getMobs(MobCategory.MONSTER).unwrap()) {
				if (entry.type == originalType) {
					spawnsList.add(new SpawnerData(newType, weight, minGroup, maxGroup));
				}
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return CamoRegistry.ADD_ENTITY_TO_SAME_BIOMES.get();
	}
}
