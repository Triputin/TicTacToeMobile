package com.example.v_triputin.tictactoemobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;

public class SettingsLoader {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_GAME_SIZE = "GameSize";
    public static final String APP_GAME_TYPE = "GameType";
    public static final String APP_ACTIVE_SKIN = "ActiveSkin";
    public static final String APP_ACTIVE_LANGUAGE = "ActiveLanguage";
    public static final String APP_CURRENT_LEVEL = "Currentlevel";
    public static class Settings{
        public int gameSize=3;
        public int activeSkin = 0;
        public int gameType=2;
        public int activeLanguage =0;
        public int currentLevel =0;


    }

public static Settings getAppSettings(MainActivity mainActivity){
        Settings settings = new Settings();
    SharedPreferences mSettings = mainActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    if (mSettings.contains(APP_GAME_SIZE)) {
        settings.gameSize = mSettings.getInt(APP_GAME_SIZE, 0);
    }
    if (mSettings.contains(APP_ACTIVE_LANGUAGE)) {
        settings.activeLanguage = mSettings.getInt(APP_ACTIVE_LANGUAGE, 0);
    }
    if (mSettings.contains(APP_GAME_TYPE)) {
        settings.gameType = mSettings.getInt(APP_GAME_TYPE, 0);
    }
    if (mSettings.contains(APP_ACTIVE_SKIN)) {
        settings.activeSkin = mSettings.getInt(APP_ACTIVE_SKIN, 0);
    }
    if (mSettings.contains(APP_CURRENT_LEVEL)) {
        settings.currentLevel = mSettings.getInt(APP_CURRENT_LEVEL, 0);
    }

    return settings;
}
public static void saveAppSettings(MainActivity mainActivity,Settings settings){
    SharedPreferences mSettings = mainActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = mSettings.edit();
    editor.putInt(APP_GAME_SIZE, settings.gameSize);
    editor.putInt(APP_ACTIVE_LANGUAGE, settings.activeLanguage );
    editor.putInt(APP_GAME_TYPE, settings.gameType);
    editor.putInt(APP_ACTIVE_SKIN, settings.activeSkin);
    editor.putInt(APP_CURRENT_LEVEL, settings.currentLevel);

    editor.apply();
}

}
