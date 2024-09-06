package me.chrr.scribble.gui;

import me.chrr.scribble.Scribble;
import me.chrr.scribble.data.ModConfigIO;
import me.chrr.scribble.model.ModConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenBuilder {

    public static ConfigScreenBuilder create() {
        return new ConfigScreenBuilder();
    }

    private ConfigScreenBuilder() {

    }

    public Screen build(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("text.scribble.screen.config.title"))
                .setSavingRunnable(() -> ModConfigIO.write(Scribble.config));

        builder.getOrCreateCategory(Text.translatable("text.scribble.screen.config.category.general.title"))
                .addEntry(
                        builder.entryBuilder()
                                .startIntField(
                                        Text.translatable("text.scribble.screen.config.category.general.edit_history_size.title"),
                                        Scribble.config.editHistorySize()
                                )
                                .setDefaultValue(ModConfig.DEFAULT.editHistorySize())
                                .setSaveConsumer((value) -> Scribble.config = Scribble.config.withEditHistorySize(value))
                                .build()
                )
                .addEntry(
                        builder.entryBuilder()
                                .startBooleanToggle(
                                        Text.translatable("text.scribble.screen.config.category.general.advanced_cursor_movement.title"),
                                        Scribble.config.isAdvancedCursorMovementEnabled()
                                )
                                .setTooltip(Text.translatable("text.scribble.screen.config.category.general.advanced_cursor_movement.description"))
                                .setDefaultValue(ModConfig.DEFAULT.isAdvancedCursorMovementEnabled())
                                .setSaveConsumer((value) -> Scribble.config = Scribble.config.withIsAdvancedCursorMovementEnabled(value))
                                .build()
                );

        return builder.build();
    }
}
