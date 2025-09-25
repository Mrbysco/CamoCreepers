package com.mrbysco.camocreepers.datagen.client;

import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class CamoModelProvider extends ModelProvider {
	public CamoModelProvider(PackOutput packOutput) {
		super(packOutput, Constants.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateSpawnEgg(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get(), 894731, 0);
	}
}
