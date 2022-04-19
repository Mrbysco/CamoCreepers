package com.mrbysco.camocreepers.config;

import com.mrbysco.camocreepers.CamoCreepers;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = CamoCreepers.MOD_ID)
@Config.LangKey("camocreepers.config.title")
public class CamoConfig {

	@Config.Comment({"General settings"})
	public static General general = new General();

	public static class General {
		@Config.RequiresMcRestart
		@Config.Comment("Override vanilla creeper spawns with the Camo Creepers [default: true]")
		public boolean overrideCreeperSpawns = false;

		@Config.RequiresMcRestart
		@Config.Comment("The spawn weight of the Camo Creeper [default: 100]")
		@Config.RangeInt(min = 1)
		public int camoCreeperWeight = 100;

		@Config.RequiresMcRestart
		@Config.Comment("The min group size of the Camo Creeper [default: 4]")
		@Config.RangeInt(min = 1)
		public int camoCreeperMin = 4;

		@Config.RequiresMcRestart
		@Config.Comment("The max group size of the Camo Creeper [default: 4]")
		@Config.RangeInt(min = 1)
		public int camoCreeperMax = 4;
	}

	@Config.Comment({"Camo settings"})
	public static Camo camo = new Camo();

	public static class Camo {
		@Config.Comment("Allow Camo Creepers to camouflage in the Nether [default: true]")
		public boolean netherCamo = true;

		@Config.Comment("Allow Camo Creepers to camouflage in the End [default: true]")
		public boolean endCamo = true;

		@Config.Comment("Allow Camo Creepers to camouflage in caves [default: true]")
		public boolean caveCamo = true;
	}

	@Mod.EventBusSubscriber(modid = CamoCreepers.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(CamoCreepers.MOD_ID)) {
				ConfigManager.sync(CamoCreepers.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
