package net.wisp.linen.gui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

public class ThirdsClient implements ClientModInitializer {

    private static KeyBinding keyBinding;
    static boolean render = false;

    static int screenWidth = 1920;
    static int screenHeight = 1080;

    private static final int color = 0x1E00DCBE; // rgba(0, 220, 190, 0.3)

    private static int scale = 1;
    private static int width = 2;

    //Vertical
    private static int left1 = (screenWidth)/(scale*3);
    private static int left2 = ((screenWidth*2)/(scale*3))-width; //-Width for x offset to make it perfect third
    private static int width1 = left1+width;
    private static int width2 = left2+width;

    //Horizontal
    private static int top1 = (screenHeight)/(scale*3);
    private static int top2 = ((screenHeight*2)/(scale*3))-width; //-Width for x offset to make it perfect third
    private static int topWidth1 = top1+width;
    private static int topWidth2 = top2+width;

    @Override
    public void onInitializeClient() {

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wisp.linen.thirds.bind", // The translation key of the keybinding name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_F4, // The keycode of the key
                "category.linen.screenshot.tools" // The translation key of the keybinding category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                render = !render;

                assert client.player != null;
                client.player.sendMessage(new TranslatableText(render ?
                                        "key.wisp.linen.thirds.activated" :
                                        "key.wisp.linen.thirds.deactivated"), false);

                screenWidth = client.getWindow().getFramebufferWidth();
                screenHeight = client.getWindow().getFramebufferHeight();

                scale = client.options.guiScale > 0 ? client.options.guiScale : 1;
                width = scale > 1 ? scale : 2;

                //Vertical
                left1 = (screenWidth)/(scale*3);
                left2 = ((screenWidth*2)/(scale*3))-width; //-Width for x offset to make it perfect third
                width1 = left1+width;
                width2 = left2+width;

                //Horizontal
                top1 = (screenHeight)/(scale*3);
                top2 = ((screenHeight*2)/(scale*3))-width; //-Width for x offset to make it perfect third
                topWidth1 = top1+width;
                topWidth2 = top2+width;
            }
        });

        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            if(render){
                /*
                    x1 = Start of Rect (Acts as Left)
                    y1 = Top
                    x2 = End of Rect (Acts as Vertical's Width with x1+width Horizontal's End with screenWidth)
                    y2 = Bottom (Acts as Height, Horizontal's Width with y1+width Vertical's End with screenHeight)
                 */
                // Vertical Lines
                DrawableHelper.fill(matrixStack, left1, 0, width1, screenHeight, color);
                DrawableHelper.fill(matrixStack, left2, 0, width2, screenHeight, color);

                //Horizontal Lines
                DrawableHelper.fill(matrixStack, 0, top1, screenWidth, topWidth1, color);
                DrawableHelper.fill(matrixStack, 0, top2, screenWidth, topWidth2, color);
            }
        });

    }
}
