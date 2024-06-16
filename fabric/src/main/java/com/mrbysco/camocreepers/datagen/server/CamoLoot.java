package com.mrbysco.camocreepers.datagen.server;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class CamoLoot extends SimpleFabricLootTableProvider {

	public CamoLoot(FabricDataOutput output) {
		super(output, LootContextParamSets.ENTITY);
	}

	@Override
	public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
		biConsumer.accept(CamoRegistry.CAMO_CREEPER.get().getDefaultLootTable(), LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.add(LootTableReference.lootTableReference(EntityType.CREEPER.getDefaultLootTable()))));
	}
}