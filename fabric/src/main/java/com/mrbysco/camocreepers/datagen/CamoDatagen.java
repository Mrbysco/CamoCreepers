package com.mrbysco.camocreepers.datagen;

import com.mrbysco.camocreepers.datagen.server.CamoBiomeTagProvider;
import com.mrbysco.camocreepers.datagen.server.CamoLoot;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CamoDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();

		pack.addProvider(CamoLoot::new);
		pack.addProvider(CamoBiomeTagProvider::new);
	}
}
