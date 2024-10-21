package me.chrr.scribble.model;

import com.google.gson.annotations.SerializedName;

public record ModConfig(
        @SerializedName("advancedCursorMovement")
        boolean isAdvancedCursorMovementEnabled,

        @SerializedName("editHistorySize")
        int editHistorySize,

        @SerializedName("copyFormattingCodes")
        boolean isCopyFormattingCodesEnabled
) {

    public static ModConfig DEFAULT = new ModConfig(
            false,
            30,
            true
    );

    public ModConfig getDefault() {
        return DEFAULT;
    }

    public ModConfig withIsAdvancedCursorMovementEnabled(boolean isAdvancedCursorMovementEnabled) {
        return new ModConfig(
                isAdvancedCursorMovementEnabled,
                this.editHistorySize,
                this.isCopyFormattingCodesEnabled
        );
    }

    public ModConfig withEditHistorySize(int editHistorySize) {
        return new ModConfig(
                this.isAdvancedCursorMovementEnabled,
                editHistorySize,
                this.isCopyFormattingCodesEnabled
        );
    }

    public ModConfig withIsCopyFormattingCodesEnabled(boolean isCopyFormattingCodesEnabled) {
        return new ModConfig(
                this.isAdvancedCursorMovementEnabled,
                this.editHistorySize,
                isCopyFormattingCodesEnabled
        );
    }

}
