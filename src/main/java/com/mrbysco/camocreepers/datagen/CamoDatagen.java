package com.mrbysco.camocreepers.datagen;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.mrbysco.camocreepers.CamoCreepers;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CamoDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();


		Map<ResourceLocation, BiomeModifier> biomeModifiers = new LinkedHashMap<>();
		biomeModifiers.put(new ResourceLocation(CamoCreepers.MOD_ID, "add_camo_creeper"), new AddEntityToSameBiomesModifier(
				EntityType.CREEPER, CamoRegistry.CAMO_CREEPER.get(), 100, 4, 4));
		biomeModifiers.put(new ResourceLocation(CamoCreepers.MOD_ID, "remove_creeper"), new RemoveCreeperModifier());

		generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
				generator, helper, CamoCreepers.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers
		));

		generator.addProvider(event.includeServer(), new CreeperLoot(generator));
	}

	private static Map<ResourceLocation, BiomeModifier> addModifierForType(EntityType<?> originalType, EntityType<?> newType, int relativeWeight) {
		return Map.of();
	}

	private static class CreeperLoot extends LootTableProvider {
		public CreeperLoot(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(Pair.of(InstrumentalEntityLoot::new, LootContextParamSets.ENTITY));
		}

		private static class InstrumentalEntityLoot extends EntityLoot {

			@Override
			protected void addTables() {
				this.add(CamoRegistry.CAMO_CREEPER.get(), LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootTableReference.lootTableReference(EntityType.CREEPER.getDefaultLootTable()))));
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entityTypeStream = CamoRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
				return (Iterable<EntityType<?>>) entityTypeStream::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
			// Don't validate as you reference another loottable
//			map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
		}
	}
}
