package com.fakepixelgarden;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AngleLockHandler {

    public static boolean locked = false;
    private static float lockedYaw;
    private static float lockedPitch;

    public static void toggle() {
        locked = !locked;
        if (locked) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                lockedYaw = player.rotationYaw;
                lockedPitch = player.rotationPitch;
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (!locked) return;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        player.rotationYaw = lockedYaw;
        player.rotationPitch = lockedPitch;
        player.prevRotationYaw = lockedYaw;
        player.prevRotationPitch = lockedPitch;
        player.rotationYawHead = lockedYaw;
        player.prevRotationYawHead = lockedYaw;
    }
}
