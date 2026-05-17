package com.mrbysco.camocreepers.datagen.client;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.world.item.Items;

public class CamoModelProvider extends FabricModelProvider {
	public CamoModelProvider(FabricPackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModels) {
		// No block models to generate
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModels) {
		itemModels.itemModelOutput.accept(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get(),
				ItemModelUtils.plainModel(
						ModelLocationUtils.getModelLocation(
								Items.CREEPER_SPAWN_EGG
						)
				)
		);
	}
}
