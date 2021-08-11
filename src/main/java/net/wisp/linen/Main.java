package net.wisp.linen;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class Main implements ModInitializer {

	private static MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		//client.inGameHud.addChatMessage(MessageType.CHAT, Text.of("test"), client.player.getUuid());
		//client.openScreen(new Thirds());
	}
}
