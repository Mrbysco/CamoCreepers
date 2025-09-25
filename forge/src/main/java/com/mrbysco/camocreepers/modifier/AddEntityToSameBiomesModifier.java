package com.mrbysco.camocreepers.modifier;

import com.mojang.serialization.MapCodec;
import com.mrbysco.camocreepers.registry.CamoModifiers;
import net.minecraft.core.Holder;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public record AddEntityToSameBiomesModifier(EntityType<?> originalType, EntityType<?> newType, int weight,
                                            int minGroup, int maxGroup) implements BiomeModifier {
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD) {
			MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
			MobSpawnSettings info = biome.value().getMobSettings();
			final WeightedList.Builder<SpawnerData> spawnsList = spawns.getSpawner(MobCategory.MONSTER);
			for (Weighted<SpawnerData> entry : info.getMobs(MobCategory.MONSTER).unwrap()) {
				if (entry.value().type() == originalType) {
					spawnsList.add(new SpawnerData(newType, minGroup, maxGroup), weight);
				}
			}
		}
	}

	@Override
	public MapCodec<? extends BiomeModifier> codec() {
		return CamoModifiers.ADD_ENTITY_TO_SAME_BIOMES.get();
	}
}
