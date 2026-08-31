package com.fakepixelgarden;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandFG extends CommandBase {

    @Override
    public String getCommandName() {
        return "fg";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fg lock | /fg bind <1-9> <command> | /fg unbind <1-9> | /fg list";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sendMsg(sender, getCommandUsage(sender));
            return;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "lock": {
                AngleLockHandler.toggle();
                sendMsg(sender, AngleLockHandler.locked ? "Angle lock ENABLED" : "Angle lock DISABLED");
                return;
            }
            case "bind": {
                if (args.length < 3) {
                    sendMsg(sender, "Usage: /fg bind <1-9> <command>");
                    return;
                }
                int slot = parseSlot(args[1], sender);
                if (slot < 0) return;

                StringBuilder cmd = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    if (i > 2) cmd.append(" ");
                    cmd.append(args[i]);
                }
                String command = cmd.toString();
                if (!command.startsWith("/")) command = "/" + command;

                FakepixelGardenMod.config.setCommand(slot, command);
                sendMsg(sender, "Slot " + (slot + 1) + " bound to: " + command
                        + " -- now go to Options > Controls > Fakepixel Garden and assign a key to \"Garden Slot " + (slot + 1) + "\"");
                return;
            }
            case "unbind": {
                if (args.length < 2) {
                    sendMsg(sender, "Usage: /fg unbind <1-9>");
                    return;
                }
                int slot = parseSlot(args[1], sender);
                if (slot < 0) return;
                FakepixelGardenMod.config.setCommand(slot, "");
                sendMsg(sender, "Slot " + (slot + 1) + " cleared");
                return;
            }
            case "list": {
                sendMsg(sender, "Current bindings:");
                for (int i = 0; i < ConfigHandler.SLOTS; i++) {
                    String c = FakepixelGardenMod.config.getCommand(i);
                    sendMsg(sender, " Slot " + (i + 1) + ": " + (c.isEmpty() ? "(empty)" : c));
                }
                return;
            }
            default: {
                sendMsg(sender, getCommandUsage(sender));
            }
        }
    }

    private int parseSlot(String arg, ICommandSender sender) {
        try {
            int slot = Integer.parseInt(arg) - 1;
            if (slot < 0 || slot >= ConfigHandler.SLOTS) {
                sendMsg(sender, "Slot must be 1-" + ConfigHandler.SLOTS);
                return -1;
            }
            return slot;
        } catch (NumberFormatException e) {
            sendMsg(sender, "Slot must be a number 1-" + ConfigHandler.SLOTS);
            return -1;
        }
    }

    private void sendMsg(ICommandSender sender, String msg) {
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "[FG] " + EnumChatFormatting.RESET + msg));
    }
        }
