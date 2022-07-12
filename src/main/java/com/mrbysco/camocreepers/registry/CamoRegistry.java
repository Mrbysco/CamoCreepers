package com.mrbysco.camocreepers.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.camocreepers.CamoCreepers;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CamoRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CamoCreepers.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, CamoCreepers.MOD_ID);
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CamoCreepers.MOD_ID);


	public static final RegistryObject<EntityType<CamoCreeperEntity>> CAMO_CREEPER = ENTITIES.register("camo_creeper", () ->
			register("camo_creeper", EntityType.Builder.<CamoCreeperEntity>of(CamoCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F).clientTrackingRange(8)));

	public static final RegistryObject<Item> CAMO_CREEPER_SPAWN_EGG = ITEMS.register("camo_creeper_spawn_egg", () ->
			new ForgeSpawnEggItem(CAMO_CREEPER, 894731, 0, itemBuilder()));

	public static void entityAttributes() {
		SpawnPlacements.register(CamoRegistry.CAMO_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(CAMO_CREEPER.get(), CamoCreeperEntity.createAttributes().build());
	}

	public static final RegistryObject<Codec<AddEntityToSameBiomesModifier>> ADD_ENTITY_TO_SAME_BIOMES = BIOME_MODIFIER_SERIALIZERS.register("add_entity_to_same_biomes", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					Registry.ENTITY_TYPE.byNameCodec().fieldOf("originalType").forGetter(AddEntityToSameBiomesModifier::originalType),
					Registry.ENTITY_TYPE.byNameCodec().fieldOf("newType").forGetter(AddEntityToSameBiomesModifier::newType),
					Codec.INT.fieldOf("weight").forGetter(AddEntityToSameBiomesModifier::weight),
					Codec.INT.fieldOf("minGroup").forGetter(AddEntityToSameBiomesModifier::minGroup),
					Codec.INT.fieldOf("maxGroup").forGetter(AddEntityToSameBiomesModifier::maxGroup)
			).apply(builder, AddEntityToSameBiomesModifier::new))
	);
	public static final RegistryObject<Codec<RemoveCreeperModifier>> REMOVE_CREEPER = BIOME_MODIFIER_SERIALIZERS.register("remove_creeper", RemoveCreeperModifier.CODEC);

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	}
}
