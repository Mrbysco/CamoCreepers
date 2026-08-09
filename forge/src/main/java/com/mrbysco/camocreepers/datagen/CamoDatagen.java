package com.mrbysco.camocreepers.datagen;

import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.datagen.client.CamoLanguageProvider;
import com.mrbysco.camocreepers.datagen.client.CamoModelProvider;
import com.mrbysco.camocreepers.datagen.server.CamoBiomeTagProvider;
import com.mrbysco.camocreepers.datagen.server.CamoLootProvider;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CamoDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new DatapackBuiltinEntriesProvider(
				packOutput, CompletableFuture.supplyAsync(CamoDatagen::getProvider), Set.of(Constants.MOD_ID)));

		generator.addProvider(true, new CamoLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new CamoBiomeTagProvider(packOutput, lookupProvider));

		generator.addProvider(true, new CamoLanguageProvider(packOutput));
		generator.addProvider(true, new CamoModelProvider(packOutput));
	}

	public static final ResourceKey<BiomeModifier> ADD_CAMO_CREEPER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			Constants.modLoc("add_camo_creeper"));
	public static final ResourceKey<BiomeModifier> REMOVE_CREEPER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			Constants.modLoc("remove_creeper"));

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
			context.register(ADD_CAMO_CREEPER, new AddEntityToSameBiomesModifier(
					EntityTypes.CREEPER, CamoRegistry.CAMO_CREEPER.get(), 100, 4, 4));
			context.register(REMOVE_CREEPER, new RemoveCreeperModifier());
		});
		registryBuilder.add(Registries.BIOME, context -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		Cloner.Factory cloner$factory = new Cloner.Factory();
		net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(p_311524_ -> p_311524_.runWithArguments(cloner$factory::addCodec));
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
	}
}
