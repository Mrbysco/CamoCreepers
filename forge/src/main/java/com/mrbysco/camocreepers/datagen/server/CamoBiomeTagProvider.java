package com.mrbysco.camocreepers.datagen.server;

import com.mrbysco.camocreepers.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CamoBiomeTagProvider extends BiomeTagsProvider {

	public CamoBiomeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                            @Nullable ExistingFileHelper existingFileHelper) {
		super(packOutput, lookupProvider, Constants.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(Constants.IS_MUSHROOM).addTag(Tags.Biomes.IS_MUSHROOM);
		this.tag(Constants.IS_SANDY).addTag(Tags.Biomes.IS_SANDY);
	}
}
