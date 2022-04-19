package com.mrbysco.camocreepers.proxy;

import com.mrbysco.camocreepers.client.renderer.CamoCreeperRenderer;
import com.mrbysco.camocreepers.entity.CamoCreeperEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void Preinit() {
		registerMobRendering();
	}

	@Override
	public void Init() {
	}

	@Override
	public void PostInit() {
	}

	public static void registerMobRendering() {
		RenderingRegistry.registerEntityRenderingHandler(CamoCreeperEntity.class, CamoCreeperRenderer.FACTORY);
	}
}
