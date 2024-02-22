package br.com.fulltime.fullarm.core.panel.components;

public class Zone {
    private boolean open;
    private boolean violated;
    private boolean annulled;
    private boolean tampered;
    private boolean shortCircuited;
    private boolean batteryLowOnWirelessSensor;
    private boolean setOff;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isViolated() {
        return violated;
    }

    public void setViolated(boolean violated) {
        this.violated = violated;
    }

    public boolean isAnnulled() {
        return annulled;
    }

    public void setAnnulled(boolean annulled) {
        this.annulled = annulled;
    }

    public boolean isTampered() {
        return tampered;
    }

    public void setTampered(boolean tampered) {
        this.tampered = tampered;
    }

    public boolean isShortCircuited() {
        return shortCircuited;
    }

    public void setShortCircuited(boolean shortCircuited) {
        this.shortCircuited = shortCircuited;
    }

    public boolean isBatteryLowOnWirelessSensor() {
        return batteryLowOnWirelessSensor;
    }

    public void setBatteryLowOnWirelessSensor(boolean batteryLowOnWirelessSensor) {
        this.batteryLowOnWirelessSensor = batteryLowOnWirelessSensor;
    }

    public boolean isSetOff() {
        return setOff;
    }

    public void setSetOff(boolean setOff) {
        this.setOff = setOff;
    }
}
