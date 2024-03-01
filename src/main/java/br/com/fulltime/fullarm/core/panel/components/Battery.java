package br.com.fulltime.fullarm.core.panel.components;

public class Battery {
    private boolean batteryOutlineOn;
    private boolean levelOneCellOn;
    private boolean levelTwoCellOn;
    private boolean levelThreeCellOn;
    private boolean batteryOutlineBlink;
    private boolean levelOneCellBlink;
    private boolean levelTwoCellBlink;
    private boolean levelThreeCellBlink;

    public boolean isBatteryOutlineOn() {
        return batteryOutlineOn;
    }

    public void setBatteryOutlineOn(boolean batteryOutlineOn) {
        this.batteryOutlineOn = batteryOutlineOn;
    }

    public boolean isLevelOneCellOn() {
        return levelOneCellOn;
    }

    public void setLevelOneCellOn(boolean levelOneCellOn) {
        this.levelOneCellOn = levelOneCellOn;
    }

    public boolean isLevelTwoCellOn() {
        return levelTwoCellOn;
    }

    public void setLevelTwoCellOn(boolean levelTwoCellOn) {
        this.levelTwoCellOn = levelTwoCellOn;
    }

    public boolean isLevelThreeCellOn() {
        return levelThreeCellOn;
    }

    public void setLevelThreeCellOn(boolean levelThreeCellOn) {
        this.levelThreeCellOn = levelThreeCellOn;
    }

    public boolean isBatteryOutlineBlink() {
        return batteryOutlineBlink;
    }

    public void setBatteryOutlineBlink(boolean batteryOutlineBlink) {
        this.batteryOutlineBlink = batteryOutlineBlink;
    }

    public boolean isLevelOneCellBlink() {
        return levelOneCellBlink;
    }

    public void setLevelOneCellBlink(boolean levelOneCellBlink) {
        this.levelOneCellBlink = levelOneCellBlink;
    }

    public boolean isLevelTwoCellBlink() {
        return levelTwoCellBlink;
    }

    public void setLevelTwoCellBlink(boolean levelTwoCellBlink) {
        this.levelTwoCellBlink = levelTwoCellBlink;
    }

    public boolean isLevelThreeCellBlink() {
        return levelThreeCellBlink;
    }

    public void setLevelThreeCellBlink(boolean levelThreeCellBlink) {
        this.levelThreeCellBlink = levelThreeCellBlink;
    }
}
