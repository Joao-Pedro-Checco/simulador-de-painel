package br.com.fulltime.fullarm.core.panel.components;

public class Zone {
    private int zoneNumber;
    private Partition partition;
    private boolean enabled;
    private boolean memory;
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

    public Partition getPartition() {
        return partition;
    }

    public void setPartition(Partition partition) {
        this.partition = partition;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isMemory() {
        return memory;
    }

    public void setMemory(boolean memory) {
        this.memory = memory;
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
