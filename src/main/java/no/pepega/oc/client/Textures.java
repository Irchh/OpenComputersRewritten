package no.pepega.oc.client;

import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Textures {
    public static Screen screen = null;

    public Textures() {
        screen = new Screen();
    }

    public class Screen extends TextureBundle {
        public List<SpriteIdentifier> Single = Arrays.asList(
                L("screen/b"),
                L("screen/b"),
                L("screen/b2"),
                L("screen/b2"),
                L("screen/b2"),
                L("screen/b2")
        );
        public List<SpriteIdentifier> SingleFront = Arrays.asList(
                L("screen/f"),
                L("screen/f2")
        );

        @Override
        protected String basePath() {
            return "block/%s";
        }
    }

    abstract class TextureBundle {
        protected abstract String basePath();

        protected SpriteIdentifier L(String name) {
            return new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier(OpenComputersRewritten.identifier, String.format(basePath(), name)));
        }
    }
}
