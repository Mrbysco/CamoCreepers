package com.mrbysco.camocreepers.platform;

import com.mrbysco.camocreepers.CamoCreepersFabric;
import com.mrbysco.camocreepers.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public boolean showNetherCamo() {
		return CamoCreepersFabric.config.get().settings.netherCamo;
	}

	@Override
	public boolean showEndCamo() {
		return CamoCreepersFabric.config.get().settings.endCamo;
	}

	@Override
	public boolean showCaveCamo() {
		return CamoCreepersFabric.config.get().settings.caveCamo;
	}
}
