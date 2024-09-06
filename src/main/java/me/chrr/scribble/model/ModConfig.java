package me.chrr.scribble.model;

public record ModConfig(
        boolean isAdvancedCursorMovementEnabled,
        int editHistorySize
) {

    public static ModConfig DEFAULT = new ModConfig(
            false,
            30
    );

    public ModConfig getDefault(){
        return DEFAULT;
    }

    public ModConfig withIsAdvancedCursorMovementEnabled(boolean isAdvancedCursorMovementEnabled) {
        return new ModConfig(
                isAdvancedCursorMovementEnabled,
                this.editHistorySize
        );
    }

    public ModConfig withEditHistorySize(int editHistorySize) {
        return new ModConfig(
                this.isAdvancedCursorMovementEnabled,
                editHistorySize
        );
    }

}
