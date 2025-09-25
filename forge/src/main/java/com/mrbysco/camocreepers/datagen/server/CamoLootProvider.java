package com.mrbysco.camocreepers.datagen.server;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import com.mrbysco.camocreepers.registration.RegistryObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class CamoLootProvider extends LootTableProvider {
	public CamoLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(CamoLootProvider.InstrumentalEntityLoot::new, LootContextParamSets.ENTITY)
		), lookupProvider);
	}

	private static class InstrumentalEntityLoot extends EntityLootSubProvider {
		protected InstrumentalEntityLoot(HolderLookup.Provider lookupProvider) {
			super(FeatureFlags.REGISTRY.allFlags(), lookupProvider);
		}

		@Override
		public void generate() {
			this.add(CamoRegistry.CAMO_CREEPER.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
							.add(NestedLootTable.lootTableReference(EntityType.CREEPER.getDefaultLootTable().get()))));
		}

		@Override
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return CamoRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
		}
	}

	@Override
	protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {

	}
}