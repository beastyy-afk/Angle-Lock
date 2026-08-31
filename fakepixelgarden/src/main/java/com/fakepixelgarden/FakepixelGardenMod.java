package com.fakepixelgarden;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FakepixelGardenMod.MODID, name = FakepixelGardenMod.NAME, version = FakepixelGardenMod.VERSION, clientSideOnly = true)
public class FakepixelGardenMod {

    public static final String MODID = "fakepixelgarden";
    public static final String NAME = "Fakepixel Garden";
    public static final String VERSION = "1.0.0";

    public static ConfigHandler config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new ConfigHandler(event.getSuggestedConfigurationFile());
        KeyBindHandler.registerKeyBindings();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new KeyBindHandler());
        MinecraftForge.EVENT_BUS.register(new AngleLockHandler());
        ClientCommandHandler.instance.registerCommand(new CommandFG());
    }
          }
