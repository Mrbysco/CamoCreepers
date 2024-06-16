package com.mrbysco.camocreepers.datagen.server;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import com.mrbysco.camocreepers.registration.RegistryObject;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class CamoLootProvider extends LootTableProvider {
	public CamoLootProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(CamoLootProvider.InstrumentalEntityLoot::new, LootContextParamSets.ENTITY)
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
		// Don't validate as we reference another loottable
	}
}