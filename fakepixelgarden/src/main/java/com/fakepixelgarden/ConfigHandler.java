package com.fakepixelgarden;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static final int SLOTS = 9;

    private final Configuration config;
    private final String[] commands = new String[SLOTS];

    public ConfigHandler(File file) {
        config = new Configuration(file);
        load();
    }

    private void load() {
        config.load();
        for (int i = 0; i < SLOTS; i++) {
            commands[i] = config.getString(
                    "slot" + (i + 1),
                    "keybinds",
                    "",
                    "Command sent when Garden Slot " + (i + 1) + " is pressed (include the leading /)"
            );
        }
        if (config.hasChanged()) {
            config.save();
        }
    }

    public String getCommand(int slot) {
        if (slot < 0 || slot >= SLOTS) return "";
        return commands[slot];
    }

    public void setCommand(int slot, String command) {
        if (slot < 0 || slot >= SLOTS) return;
        commands[slot] = command;
        config.get("keybinds", "slot" + (slot + 1), "").set(command);
        config.save();
    }
  }
