package com.mrbysco.camocreepers.datagen.client;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class CamoLanguageProvider extends FabricLanguageProvider {
	public CamoLanguageProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(dataOutput, completableFuture);
	}

	@Override
	public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
		builder.add(CamoRegistry.CAMO_CREEPER.get(), "Camo Creeper");
		builder.add(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get(), "Camo Creeper Spawn Egg");
		builder.add("text.autoconfig.camocreepers.title", "Camo Creepers");
		builder.add("text.autoconfig.camocreepers.option.general", "General");
		builder.add("text.autoconfig.camocreepers.option.general.overrideCreeperSpawns", "Override Creeper Spawns");
		builder.add("text.autoconfig.camocreepers.option.settings", "Settings");
		builder.add("text.autoconfig.camocreepers.option.settings.netherCamo", "Nether Camo");
		builder.add("text.autoconfig.camocreepers.option.settings.endCamo", "End Camo");
		builder.add("text.autoconfig.camocreepers.option.settings.caveCamo", "Cave Camo");
	}
}
