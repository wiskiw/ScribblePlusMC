package me.chrr.scribble;

import me.chrr.scribble.data.ModConfigIO;
import me.chrr.scribble.model.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scribble implements ClientModInitializer {
    public static final String MOD_ID = "scribble-plus";
    public static Logger LOGGER = LogManager.getLogger();

    public static final String COMPATIBLE_MOD_ID_SYMBOL_CHAT = "symbol-chat";
    public static final String COMPATIBLE_MOD_ID_FIXBOOKGUI = "fixbookgui";
    public static final String COMPATIBLE_MOD_ID_YACL = "yet_another_config_lib_v3";

    private static ModConfig config;

    public static boolean shouldCenter = false;

    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isModLoaded(COMPATIBLE_MOD_ID_FIXBOOKGUI)) {
            LOGGER.info("FixBookGUI is centering the book screen, adapting...");
            Scribble.shouldCenter = true;
        }
    }

    public static ModConfig getConfig() {
        if (config == null) {
            config = ModConfigIO.read();
        }
        return config;
    }

    public static void setConfig(ModConfig config) {
        Scribble.config = config;
    }

    public static Identifier id(String path) {
        //? if >=1.21 {
        return Identifier.of(MOD_ID, path);
        //?} else
        /*return new Identifier(MOD_ID, path);*/
    }
}
