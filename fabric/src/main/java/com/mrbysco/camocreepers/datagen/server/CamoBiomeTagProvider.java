package com.mrbysco.camocreepers.datagen.server;

import com.mrbysco.camocreepers.Constants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class CamoBiomeTagProvider extends FabricTagProvider<Biome> {

	public CamoBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.BIOME, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(Constants.IS_MUSHROOM).add(Biomes.MUSHROOM_FIELDS);
		this.tag(Constants.IS_SANDY).add(Biomes.DESERT, Biomes.BEACH, Biomes.BADLANDS, Biomes.WOODED_BADLANDS);
	}
}
