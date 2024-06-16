package com.mrbysco.camocreepers.platform;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.platform.services.IPlatformHelper;
import com.mrbysco.camocreepers.registration.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public <T extends Mob> SpawnEggItem buildSpawnEgg(RegistryObject<EntityType<T>> type,
	                                                  int backgroundColor, int highlightColor, Item.Properties props) {
		return new DeferredSpawnEggItem(type, backgroundColor, highlightColor, props);
	}

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
