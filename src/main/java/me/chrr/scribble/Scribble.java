package me.chrr.scribble;

import me.chrr.scribble.data.ModConfigIO;
import me.chrr.scribble.model.ModConfig;
import me.chrr.scribble.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Scribble implements ClientModInitializer {
    public static final String MOD_ID = "scribble-plus";
    public static Logger LOGGER = LogManager.getLogger();

    public static final String COMPATIBLE_MOD_ID_SYMBOL_CHAT = "symbol-chat";
    public static final String COMPATIBLE_MOD_ID_FIXBOOKGUI = "fixbookgui";
    public static final String COMPATIBLE_MOD_ID_YACL = "yet_another_config_lib_v3";

    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static boolean shouldCenter = false;

    public static ModConfig config = ModConfigIO.read();

    @Override
    public void onInitializeClient() {
        try {
            CONFIG_MANAGER.load();
        } catch (IOException e) {
            LOGGER.error("failed to load config", e);
        }

        if (FabricLoader.getInstance().isModLoaded(COMPATIBLE_MOD_ID_FIXBOOKGUI)) {
            LOGGER.info("FixBookGUI is centering the book screen, adapting...");
            Scribble.shouldCenter = true;
        }
    }

    public static Identifier id(String path) {
        //? if >=1.21 {
        return Identifier.of(MOD_ID, path);
        //?} else
        /*return new Identifier(MOD_ID, path);*/
    }
}
