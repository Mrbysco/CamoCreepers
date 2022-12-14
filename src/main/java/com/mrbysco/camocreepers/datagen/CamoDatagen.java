package com.mrbysco.camocreepers.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.mrbysco.camocreepers.CamoCreepers;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CamoDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		HolderLookup.Provider provider = getProvider();
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, provider);
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();


		Map<ResourceLocation, BiomeModifier> biomeModifiers = new LinkedHashMap<>();
		biomeModifiers.put(new ResourceLocation(CamoCreepers.MOD_ID, "add_camo_creeper"), new AddEntityToSameBiomesModifier(
				EntityType.CREEPER, CamoRegistry.CAMO_CREEPER.get(), 100, 4, 4));
		biomeModifiers.put(new ResourceLocation(CamoCreepers.MOD_ID, "remove_creeper"), new RemoveCreeperModifier());

		generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
				packOutput, helper, CamoCreepers.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers
		));

		generator.addProvider(event.includeServer(), new CreeperLoot(packOutput));
	}

	private static HolderLookup.Provider getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
	}

	private static class CreeperLoot extends LootTableProvider {
		public CreeperLoot(PackOutput packOutput) {
			super(packOutput, Set.of(), List.of(
					new SubProviderEntry(InstrumentalEntityLoot::new, LootContextParamSets.ENTITY)
			));
		}

		private static class InstrumentalEntityLoot extends EntityLootSubProvider {
			protected InstrumentalEntityLoot() {
				super(FeatureFlags.REGISTRY.allFlags());
			}

			@Override
			public void generate() {
				this.add(CamoRegistry.CAMO_CREEPER.get(), LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootTableReference.lootTableReference(EntityType.CREEPER.getDefaultLootTable()))));
			}

			@Override
			protected Stream<EntityType<?>> getKnownEntityTypes() {
				return CamoRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
			// Don't validate as you reference another loottable
//			map.forEach((name, table) -> LootTables.validate(validationContext, name, table));
		}
	}
}
