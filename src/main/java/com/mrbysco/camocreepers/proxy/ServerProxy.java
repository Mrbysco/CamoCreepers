package com.mrbysco.camocreepers.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ServerProxy extends CommonProxy {
	@Override
	public void Preinit() {
		super.Preinit();
	}

	@Override
	public void Init() {
		super.Init();
	}

	@Override
	public void PostInit() {
		super.PostInit();
	}
}
