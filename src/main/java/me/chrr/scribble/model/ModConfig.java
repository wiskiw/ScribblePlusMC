package me.chrr.scribble.model;

public record ModConfig(
        boolean isAdvancedCursorMovementEnabled
) {

    public static ModConfig DEFAULT = new ModConfig(false);

    public ModConfig withIsAdvancedCursorMovementEnabled(boolean isAdvancedCursorMovementEnabled) {
        return new ModConfig(isAdvancedCursorMovementEnabled);
    }

}
