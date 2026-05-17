package com.mrbysco.camocreepers.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public class CamoConfig {
	public static class Common {
		public final BooleanValue overrideCreeperSpawns;

		public final BooleanValue netherCamo;
		public final BooleanValue endCamo;
		public final BooleanValue caveCamo;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			overrideCreeperSpawns = builder
					.comment("Override vanilla creeper spawns with the Camo Creepers [default: true]")
					.define("overrideCreeperSpawns", true);

			builder.pop();
			builder.comment("Camo settings")
					.push("Camo");

			netherCamo = builder
					.comment("Allow Camo Creepers to camouflage in the Nether [default: true]")
					.define("netherCamo", true);

			endCamo = builder
					.comment("Allow Camo Creepers to camouflage in the End [default: true]")
					.define("endCamo", true);

			caveCamo = builder
					.comment("Allow Camo Creepers to camouflage in caves [default: true]")
					.define("caveCamo", true);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
