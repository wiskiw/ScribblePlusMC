package me.chrr.scribble.config;

import com.google.gson.annotations.SerializedName;

public class Config {

    public static Config DEFAULT = new Config();

    @SerializedName("version")
    public int version = 2;

    @SerializedName("copy_formatting_codes")
    public boolean copyFormattingCodes = true;

    @SerializedName("center_book_gui")
    public boolean centerBookGui = true;

    @SerializedName("show_save_load_buttons")
    public boolean showSaveLoadButtons = true;

    @SerializedName("edit_history_size")
    public int editHistorySize = 32;

    @SerializedName("use_enhanced_cursor_movement")
    public boolean useEnhancedCursorMovement = false;

    public void upgrade() {
        this.version = DEFAULT.version;
    }
}
