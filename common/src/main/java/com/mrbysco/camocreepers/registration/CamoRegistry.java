package com.mrbysco.camocreepers.registration;

import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.entity.CamoCreeper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

/**
 * This class is used to register entities and items.
 */
public class CamoRegistry {

	public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(BuiltInRegistries.ITEM, Constants.MOD_ID);
	public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);

	public static final RegistryObject<EntityType<CamoCreeper>> CAMO_CREEPER = ENTITY_TYPES.register("camo_creeper", () ->
			register("camo_creeper", EntityType.Builder.<CamoCreeper>of(CamoCreeper::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F).clientTrackingRange(8)));

	public static final RegistryObject<Item> CAMO_CREEPER_SPAWN_EGG = ITEMS.register("camo_creeper_spawn_egg", () ->
			new SpawnEggItem(new Item.Properties().spawnEgg(CAMO_CREEPER.get()).setId(getItemKey("camo_creeper_spawn_egg"))));


	private static ResourceKey<Item> getItemKey(String name) {
		return ResourceKey.create(Registries.ITEM, Constants.modLoc(name));
	}

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(getEntityKey(id));
	}

	private static ResourceKey<EntityType<?>> getEntityKey(String name) {
		return ResourceKey.create(Registries.ENTITY_TYPE, Constants.modLoc(name));
	}

	// Called in the mod initializer / constructor in order to make sure that items are registered
	public static void loadClass() {
	}
}
