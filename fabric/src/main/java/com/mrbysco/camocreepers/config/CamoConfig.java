package com.mrbysco.camocreepers.config;

import com.mrbysco.camocreepers.Constants;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Constants.MOD_ID)
public class CamoConfig implements ConfigData {

	@CollapsibleObject
	public General general = new General();

	@CollapsibleObject
	public Settings settings = new Settings();

	public static class General {
		@Comment("Override vanilla creeper spawns with the Camo Creepers [default: true]")
		public boolean overrideCreeperSpawns = true;
	}

	public static class Settings {
		@Comment("Allow Camo Creepers to camouflage in the Nether [default: true]")
		public boolean netherCamo = true;

		@Comment("Allow Camo Creepers to camouflage in the End [default: true]")
		public boolean endCamo = true;

		@Comment("Allow Camo Creepers to camouflage in caves [default: true]")
		public boolean caveCamo = true;
	}
}