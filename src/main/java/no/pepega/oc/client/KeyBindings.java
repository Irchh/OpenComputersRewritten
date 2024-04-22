package no.pepega.oc.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Language;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static KeyBinding analyzeCopyAddress;
    private static KeyBinding clipboardPaste;
    public static KeyBinding extendedTooltip;

    public static void init() {
        /*analyzeCopyAddress = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.opencomputers.analyzeCopyAddress",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                "category.examplemod.test"
        ));*/
        /*clipboardPaste = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.opencomputers.clipboardPaste",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
                "category.examplemod.test"
        ));*/
        extendedTooltip = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.opencomputers.extendedTooltip",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                "category.examplemod.test"
        ));
    }

    public static boolean showExtendedTooltips() {
        // TODO: Figure out how to get "boundKey" instead of "defaultKey". This should be open but whatever lol.
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), extendedTooltip.getDefaultKey().getCode());
    }

    public static Object getKeyBindingName(KeyBinding keyBinding) {
        return Language.getInstance().get(keyBinding.getBoundKeyTranslationKey());
    }
}
