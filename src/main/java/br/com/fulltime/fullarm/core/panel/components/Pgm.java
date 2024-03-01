package br.com.fulltime.fullarm.core.panel.components;

public class Pgm {
    private int pgmNumber;
    private boolean turnedOn;

    public int getPgmNumber() {
        return pgmNumber;
    }

    public void setPgmNumber(int pgmNumber) {
        this.pgmNumber = pgmNumber;
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }
}
