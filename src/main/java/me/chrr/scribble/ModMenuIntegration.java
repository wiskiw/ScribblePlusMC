package me.chrr.scribble;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.chrr.scribble.gui.ConfigScreenBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return parent -> ConfigScreenBuilder.create().build(parent);
        } else {
            return null;
        }
    }
}
