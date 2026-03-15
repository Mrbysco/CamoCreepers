package com.mrbysco.camocreepers.client.renderer;

import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class CamoCreeperRenderState extends CreeperRenderState {
	public BlockState feetState = null;
	public Holder<Biome> biome = null;
	public int baseColor = -1;
	public int foliageColor = -1;
	public int seaLevel = -1;
	public boolean canSeeSky = false;
}
