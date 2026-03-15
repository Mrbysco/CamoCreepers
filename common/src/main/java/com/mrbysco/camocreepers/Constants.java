package com.mrbysco.camocreepers;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "camocreepers";
	public static final String MOD_NAME = "Camo Creepers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final TagKey<Biome> IS_MUSHROOM = TagKey.create(Registries.BIOME, modLoc("is_mushroom"));
	public static final TagKey<Biome> IS_SANDY = TagKey.create(Registries.BIOME, modLoc("is_sandy"));

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}