package net.wisp.linen.mixin;

import net.minecraft.client.MinecraftClient;
import net.wisp.linen.utils.Utils;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.Consumer;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotIntercept {

	private static final MinecraftClient client = MinecraftClient.getInstance();

	@Inject(at = @At("RETURN"), method = "saveScreenshot")
	private static void saveScreenshot(File gameDirectory, Framebuffer framebuffer, Consumer<Text> messageReceiver, CallbackInfo ci) {

		File screenshotDir = new File(gameDirectory, "screenshots");

		File latestFile = Utils.getLatestFileFromDirectory(screenshotDir);

		try {
			Files.setAttribute(latestFile.toPath(), "user:headYaw", client.player != null ? Float.toString(client.player.headYaw).getBytes() : 0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println(latestFile.getAbsolutePath());
	}
}
