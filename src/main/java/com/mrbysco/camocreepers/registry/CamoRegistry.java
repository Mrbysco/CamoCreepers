package com.mrbysco.camocreepers.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.camocreepers.CamoCreepers;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CamoRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CamoCreepers.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, CamoCreepers.MOD_ID);
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CamoCreepers.MOD_ID);


	public static final DeferredHolder<EntityType<?>, EntityType<CamoCreeperEntity>> CAMO_CREEPER = ENTITY_TYPES.register("camo_creeper", () ->
			register("camo_creeper", EntityType.Builder.<CamoCreeperEntity>of(CamoCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F).clientTrackingRange(8)));

	public static final DeferredItem<Item> CAMO_CREEPER_SPAWN_EGG = ITEMS.register("camo_creeper_spawn_egg", () ->
			new DeferredSpawnEggItem(CAMO_CREEPER, 894731, 0, (new Item.Properties())));

	public static void registerEntityAttributes(SpawnPlacementRegisterEvent event) {
		event.register(CamoRegistry.CAMO_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
	}

	public static void registerSpawnPlacements(EntityAttributeCreationEvent event) {
		event.put(CAMO_CREEPER.get(), CamoCreeperEntity.createAttributes().build());
	}

	public static final Supplier<Codec<AddEntityToSameBiomesModifier>> ADD_ENTITY_TO_SAME_BIOMES = BIOME_MODIFIER_SERIALIZERS.register("add_entity_to_same_biomes", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("originalType").forGetter(AddEntityToSameBiomesModifier::originalType),
					BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("newType").forGetter(AddEntityToSameBiomesModifier::newType),
					Codec.INT.fieldOf("weight").forGetter(AddEntityToSameBiomesModifier::weight),
					Codec.INT.fieldOf("minGroup").forGetter(AddEntityToSameBiomesModifier::minGroup),
					Codec.INT.fieldOf("maxGroup").forGetter(AddEntityToSameBiomesModifier::maxGroup)
			).apply(builder, AddEntityToSameBiomesModifier::new))
	);
	public static final Supplier<Codec<RemoveCreeperModifier>> REMOVE_CREEPER = BIOME_MODIFIER_SERIALIZERS.register("remove_creeper", RemoveCreeperModifier.CODEC);

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}
}
