package com.mrbysco.camocreepers.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.camocreepers.Constants;
import com.mrbysco.camocreepers.modifier.AddEntityToSameBiomesModifier;
import com.mrbysco.camocreepers.modifier.RemoveCreeperModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CamoModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

	public static final RegistryObject<Codec<AddEntityToSameBiomesModifier>> ADD_ENTITY_TO_SAME_BIOMES = BIOME_MODIFIER_SERIALIZERS.register("add_entity_to_same_biomes", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("originalType").forGetter(AddEntityToSameBiomesModifier::originalType),
					BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("newType").forGetter(AddEntityToSameBiomesModifier::newType),
					Codec.INT.fieldOf("weight").forGetter(AddEntityToSameBiomesModifier::weight),
					Codec.INT.fieldOf("minGroup").forGetter(AddEntityToSameBiomesModifier::minGroup),
					Codec.INT.fieldOf("maxGroup").forGetter(AddEntityToSameBiomesModifier::maxGroup)
			).apply(builder, AddEntityToSameBiomesModifier::new))
	);
	public static final RegistryObject<Codec<RemoveCreeperModifier>> REMOVE_CREEPER = BIOME_MODIFIER_SERIALIZERS.register("remove_creeper", RemoveCreeperModifier.CODEC);
}
