package com.mrbysco.camocreepers.datagen;

import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.datagen.server.CamoBiomeTagProvider;
import com.mrbysco.camocreepers.datagen.server.CamoLootProvider;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CamoDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
				packOutput, CompletableFuture.supplyAsync(CamoDatagen::getProvider), Set.of(Constants.MOD_ID)));

		generator.addProvider(event.includeServer(), new CamoLootProvider(packOutput));
		generator.addProvider(event.includeServer(), new CamoBiomeTagProvider(packOutput, lookupProvider, helper));
	}

	public static final ResourceKey<BiomeModifier> ADD_CAMO_CREEPER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Constants.MOD_ID, "add_camo_creeper"));
	public static final ResourceKey<BiomeModifier> REMOVE_CREEPER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Constants.MOD_ID, "remove_creeper"));

	private static HolderLookup.Provider getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
			context.register(ADD_CAMO_CREEPER, new AddEntityToSameBiomesModifier(
					EntityType.CREEPER, CamoRegistry.CAMO_CREEPER.get(), 100, 4, 4));
			context.register(REMOVE_CREEPER, new RemoveCreeperModifier());
		});
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
	}
}
