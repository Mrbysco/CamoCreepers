package com.mrbysco.camocreepers.platform;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.platform.services.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public boolean showNetherCamo() {
		return CamoConfig.COMMON.netherCamo.get();
	}

	@Override
	public boolean showEndCamo() {
		return CamoConfig.COMMON.endCamo.get();
	}

	@Override
	public boolean showCaveCamo() {
		return CamoConfig.COMMON.caveCamo.get();
	}
}
