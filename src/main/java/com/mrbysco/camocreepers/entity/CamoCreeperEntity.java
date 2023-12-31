package com.mrbysco.camocreepers.entity;

import com.mrbysco.camocreepers.registry.CamoRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

public class CamoCreeperEntity extends Creeper {
	public CamoCreeperEntity(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	public CamoCreeperEntity(Level level) {
		super(CamoRegistry.CAMO_CREEPER.get(), level);
	}
}
