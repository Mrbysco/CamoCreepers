package com.mrbysco.camocreepers.entity;

import com.mrbysco.camocreepers.CamoRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.world.World;

public class CamoCreeperEntity extends CreeperEntity {
	public CamoCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public CamoCreeperEntity(World worldIn) {
		super(CamoRegistry.CAMO_CREEPER.get(), worldIn);
	}
}
