package com.fakepixelgarden;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class KeyBindHandler {

    private static final String CATEGORY = "Fakepixel Garden";
    public static final KeyBinding[] SLOT_KEYS = new KeyBinding[ConfigHandler.SLOTS];

    public static void registerKeyBindings() {
        for (int i = 0; i < ConfigHandler.SLOTS; i++) {
            SLOT_KEYS[i] = new KeyBinding("Garden Slot " + (i + 1), Keyboard.KEY_NONE, CATEGORY);
            ClientRegistry.registerKeyBinding(SLOT_KEYS[i]);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        for (int i = 0; i < ConfigHandler.SLOTS; i++) {
            while (SLOT_KEYS[i].isPressed()) {
                String cmd = FakepixelGardenMod.config.getCommand(i);
                if (cmd != null && !cmd.trim().isEmpty()) {
                    mc.thePlayer.sendChatMessage(cmd);
                }
            }
        }
    }
}
