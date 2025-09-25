package com.mrbysco.camocreepers.datagen.client;

import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

public class CamoLanguageProvider extends LanguageProvider {

	public CamoLanguageProvider(PackOutput packOutput) {
		super(packOutput, Constants.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(CamoRegistry.CAMO_CREEPER.get(), "Camo Creeper");
		add(CamoRegistry.CAMO_CREEPER_SPAWN_EGG.get(), "Camo Creeper Spawn Egg");

		addConfig("General", "General", "General settings");
		addConfig("overrideCreeperSpawns", "Override Creeper Spawns", "Override vanilla creeper spawns with the Camo Creepers");
		addConfig("Camo", "Camo", "Camo settings");
		addConfig("netherCamo", "Nether Camo", "Allow Camo Creepers to camouflage in the Nether");
		addConfig("endCamo", "End Camo", "Allow Camo Creepers to camouflage in the End");
		addConfig("caveCamo", "Cave Camo", "Allow Camo Creepers to camouflage in caves");
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(Constants.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(Constants.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
