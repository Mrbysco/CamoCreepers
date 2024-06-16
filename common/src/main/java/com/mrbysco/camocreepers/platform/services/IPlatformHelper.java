package com.mrbysco.camocreepers.platform.services;

import com.mrbysco.camocreepers.registration.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public interface IPlatformHelper {

	/**
	 * Build a spawn egg for the given entity type
	 *
	 * @param type            The entity type
	 * @param backgroundColor The background color
	 * @param highlightColor  The highlight color
	 * @param props           The item properties
	 * @return The spawn egg
	 */
	<T extends Mob> SpawnEggItem buildSpawnEgg(RegistryObject<EntityType<T>> type,
	                                           int backgroundColor, int highlightColor, Item.Properties props);

	/**
	 * @return if `netherCamo` is true in the config
	 */
	boolean showNetherCamo();

	/**
	 * @return if `endCamo` is true in the config
	 */
	boolean showEndCamo();

	/**
	 * @return if `caveCamo` is true in the config
	 */
	boolean showCaveCamo();
}
