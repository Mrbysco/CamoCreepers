package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = CamoCreepers.MOD_ID,
		name = CamoCreepers.MOD_NAME,
		acceptedMinecraftVersions = CamoCreepers.ACCEPTED_VERSIONS)
public class CamoCreepers {
	public static final String MOD_ID = "camocreepers";
	public static final String MOD_PREFIX = MOD_ID + ":";
	public static final String MOD_NAME = "Camo Creepers";
	public static final String ACCEPTED_VERSIONS = "[1.12]";
	public static final String CLIENT_PROXY_CLASS = "com.mrbysco.camocreepers.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.mrbysco.camocreepers.proxy.ServerProxy";

	public static final Logger LOGGER = LogManager.getLogger();

	@Instance(CamoCreepers.MOD_ID)
	public static CamoCreepers instance;

	@SidedProxy(clientSide = CamoCreepers.CLIENT_PROXY_CLASS, serverSide = CamoCreepers.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		LOGGER.info("Registering CamoCreepers Config");
		MinecraftForge.EVENT_BUS.register(new CamoConfig());

		CamoRegistry.register();

		proxy.Preinit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		CamoRegistry.registerBiomes();

		proxy.Init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.PostInit();
	}
}
