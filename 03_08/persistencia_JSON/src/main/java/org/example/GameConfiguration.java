package org.example;

public class GameConfiguration {
    private String playerName;
    private int levelDifficulty;
    private boolean audioEnabled;
    private String screenResolution;

    public GameConfiguration() {
        this.playerName = "Player1";
        this.levelDifficulty = 2;
        audioEnabled = true;
        this.screenResolution = "1920x1080";
    }

    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public int getLevelDifficulty() {
        return levelDifficulty;
    }
    public void setLevelDifficulty(int levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }
    public boolean isAudioEnabled() {
        return audioEnabled;
    }
    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }
    public String getScreenResolution() {
        return screenResolution;
    }
    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    @Override
    public String toString() {
        String statusAudio = audioEnabled ? "Enabled" : "Disabled";

        return "gameConfig {\n" +
                "playerName='" + playerName + "'" +
                "\nlevelDifficulty=" + levelDifficulty +
                "\naudioEnabled=" + statusAudio +
                "\nscreenResolution='" + screenResolution + "'" +
                "\n}";
    }
}
