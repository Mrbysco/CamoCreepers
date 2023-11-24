package com.mrbysco.camocreepers.modifier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

import java.util.List;

public class RemoveCreeperModifier implements BiomeModifier {
	public static final Supplier<Codec<RemoveCreeperModifier>> CODEC = Suppliers.memoize(() -> Codec.unit(() -> RemoveCreeperModifier.INSTANCE));
	public static final RemoveCreeperModifier INSTANCE = new RemoveCreeperModifier();

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.REMOVE && CamoConfig.COMMON.overrideCreeperSpawns.get()) {
			MobSpawnSettingsBuilder spawnBuilder = builder.getMobSpawnSettings();
			for (MobCategory category : MobCategory.values()) {
				List<SpawnerData> spawns = spawnBuilder.getSpawner(category);
				spawns.removeIf(spawnerData -> spawnerData.type == EntityType.CREEPER);
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return CamoRegistry.REMOVE_CREEPER.get();
	}
}
