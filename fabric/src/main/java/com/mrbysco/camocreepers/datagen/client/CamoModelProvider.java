package com.mrbysco.camocreepers.datagen.client;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class CamoModelProvider extends FabricModelProvider {
	public CamoModelProvider(FabricDataOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModels) {
		// No block models to generate
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModels) {
		itemModels.generateSpawnEgg(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get(), 894731, 0);
	}
}
