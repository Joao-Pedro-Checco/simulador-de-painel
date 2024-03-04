package br.com.fulltime.fullarm.core.panel.components;

public class Zone {
    private int zoneNumber;
    private int partition;
    private boolean open;
    private boolean violated;
    private boolean bypassed;
    private boolean tampered;
    private boolean shortCircuit;
    private boolean batteryLowOnWirelessSensor;

    public int getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

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

    public boolean isBypassed() {
        return bypassed;
    }

    public void setBypassed(boolean bypassed) {
        this.bypassed = bypassed;
    }

    public boolean isTampered() {
        return tampered;
    }

    public void setTampered(boolean tampered) {
        this.tampered = tampered;
    }

    public boolean isShortCircuit() {
        return shortCircuit;
    }

    public void setShortCircuit(boolean shortCircuit) {
        this.shortCircuit = shortCircuit;
    }

    public boolean isBatteryLowOnWirelessSensor() {
        return batteryLowOnWirelessSensor;
    }

    public void setBatteryLowOnWirelessSensor(boolean batteryLowOnWirelessSensor) {
        this.batteryLowOnWirelessSensor = batteryLowOnWirelessSensor;
    }
}
