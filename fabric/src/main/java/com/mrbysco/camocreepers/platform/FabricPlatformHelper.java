package com.mrbysco.camocreepers.platform;

import com.mrbysco.camocreepers.CamoCreepersFabric;
import com.mrbysco.camocreepers.item.SupplierSpawnEggItem;
import com.mrbysco.camocreepers.platform.services.IPlatformHelper;
import com.mrbysco.camocreepers.registration.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public <T extends Mob> SpawnEggItem buildSpawnEgg(RegistryObject<EntityType<T>> type,
	                                                  int backgroundColor, int highlightColor, Item.Properties props) {
		return new SupplierSpawnEggItem<>(type, backgroundColor, highlightColor, props);
	}

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
