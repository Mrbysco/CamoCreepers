package com.mrbysco.camocreepers.entity;

import com.mrbysco.camocreepers.registration.CamoRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

public class CamoCreeper extends Creeper {
	public CamoCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public EntityType<?> getType() {
		return CamoRegistry.CAMO_CREEPER.get();
	}
}
