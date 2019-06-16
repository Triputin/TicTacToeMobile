package com.example.v_triputin.tictactoemobile;

public class LevelSettings {
private int gameSize;
private int gameType;

    public LevelSettings(int gameSize, int gameType) {
        this.gameSize = gameSize;
        this.gameType = gameType;
    }

    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }
}
