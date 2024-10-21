package me.chrr.scribble.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.chrr.scribble.Scribble;
import me.chrr.scribble.model.ModConfig;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class ModConfigIO {

    private static final Path configFilePath = FabricLoader.getInstance()
            .getConfigDir()
            .resolve(Scribble.MOD_ID + ".json");

    public static ModConfig read() {
        try {
            FileInputStream configFileInputStream = new FileInputStream(configFilePath.toFile());
            String jsonConfig = IOUtils.toString(configFileInputStream, Charset.defaultCharset());

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(jsonConfig, ModConfig.class);

        } catch (JsonSyntaxException e) {
            Scribble.LOGGER.warn("Failed to parse config file, use default.", e);
            return ModConfig.DEFAULT;

        } catch (IOException e) {
            Scribble.LOGGER.debug("Failed to read config file, use default.", e);
            return ModConfig.DEFAULT;
        }
    }

    public static void write(ModConfig modConfig) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        String jsonConfig = gson.toJson(modConfig);

        try {
            FileOutputStream configFileOutputStream = new FileOutputStream(configFilePath.toFile());
            IOUtils.write(jsonConfig, configFileOutputStream, Charset.defaultCharset());
        } catch (IOException e) {
            Scribble.LOGGER.error("Failed to write config file.", e);
        }
    }
}
