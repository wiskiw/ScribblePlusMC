package me.chrr.scribble.gui;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import me.chrr.scribble.Scribble;
import me.chrr.scribble.data.ModConfigIO;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenBuilder {

    public static ConfigScreenBuilder create() {
        return new ConfigScreenBuilder();
    }

    private static YetAnotherConfigLib createYACLBuilder() {
        return YetAnotherConfigLib.createBuilder()
                .save(() -> ModConfigIO.write(Scribble.config))
                .title(Text.translatable("text.scribble.screen.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("text.scribble.screen.config.category.general.title"))
                        .option(
                                Option.<Boolean>createBuilder()
                                        .name(Text.translatable("text.scribble.screen.config.category.general.advanced_cursor_movement.title"))
                                        .description(OptionDescription.of(Text.translatable("text.scribble.screen.config.category.general.advanced_cursor_movement.description")))
                                        .binding(
                                                Scribble.config.getDefault().isAdvancedCursorMovementEnabled(),
                                                () -> Scribble.config.isAdvancedCursorMovementEnabled(),
                                                value -> Scribble.config = Scribble.config.withIsAdvancedCursorMovementEnabled(value)
                                        )
                                        .controller((opt) -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                                        .build()
                        )
                        .option(
                                Option.<Integer>createBuilder()
                                        .name(Text.translatable("text.scribble.screen.config.category.general.edit_history_size.title"))
                                        .binding(
                                                Scribble.config.getDefault().editHistorySize(),
                                                () -> Scribble.config.editHistorySize(),
                                                value -> Scribble.config = Scribble.config.withEditHistorySize(value)
                                        )
                                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                                .range(0, 512)
                                        )
                                        .build()
                        )
                        .build())
                .build();
    }

    private ConfigScreenBuilder() {

    }

    public Screen build(Screen parent) {
        return createYACLBuilder().generateScreen(parent);
    }
}
