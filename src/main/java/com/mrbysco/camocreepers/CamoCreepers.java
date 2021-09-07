package com.mrbysco.camocreepers;

import com.mrbysco.camocreepers.client.ClientHandler;
import com.mrbysco.camocreepers.config.CamoConfig;
import com.mrbysco.camocreepers.item.CustomSpawnEggItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CamoCreepers.MOD_ID)
public class CamoCreepers {
    public static final String MOD_ID = "camocreepers";
    public static final Logger LOGGER = LogManager.getLogger();

    public CamoCreepers() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CamoConfig.commonSpec);
        eventBus.register(CamoConfig.class);

        eventBus.addListener(this::setup);

        CamoRegistry.ITEMS.register(eventBus);
        CamoRegistry.ENTITIES.register(eventBus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, CamoRegistry::addSpawn);
        eventBus.addListener(CamoRegistry::registerEntityAttributes);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::onClientSetup);
            eventBus.addListener(ClientHandler::registerItemColors);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for(RegistryObject<Item> registryObject : CamoRegistry.ITEMS.getEntries()) {
                if(registryObject.get() instanceof CustomSpawnEggItem) {
                    CustomSpawnEggItem spawnEgg = (CustomSpawnEggItem)registryObject.get();
                    SpawnEggItem.EGGS.put(spawnEgg.entityType.get(), spawnEgg);
                }
            }
        });
    }
}
