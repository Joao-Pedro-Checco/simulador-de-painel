package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.core.panel.components.*;

import java.time.LocalDateTime;
import java.util.List;

public class PanelStatus {
    private List<Zone> zones;
    private String panelModel;
    private String firmwareVersion;
    private boolean partitioned;
    private List<Boolean> activatedPartitions;
    private boolean problemsDetected;
    private boolean sirenTurnedOn;
    private boolean setOffZones;
    private boolean panelActivated;
    private LocalDateTime dateTime;
    private boolean electricNetworkFail;
    private boolean lowBattery;
    private boolean batteryAbsentOrInverted;
    private boolean shortCircuitedBattery;
    private boolean auxiliaryOutputOverload;
    private List<Keyboard> keyboards;
    private List<Receiver> receivers;
    private List<Expander> expanders;
    private BatteryIcon batteryIcon;
    private boolean sirenWireCut;
    private boolean sirenWireShortCircuited;
    private boolean phoneLineCut;
    private boolean failureToCommunicateEvent;
    private List<Pgm> pgmList;

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public String getPanelModel() {
        return panelModel;
    }

    public void setPanelModel(String panelModel) {
        this.panelModel = panelModel;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public boolean isPartitioned() {
        return partitioned;
    }

    public void setPartitioned(boolean partitioned) {
        this.partitioned = partitioned;
    }

    public List<Boolean> getActivatedPartitions() {
        return activatedPartitions;
    }

    public void setActivatedPartitions(List<Boolean> activatedPartitions) {
        this.activatedPartitions = activatedPartitions;
    }

    public boolean isProblemsDetected() {
        return problemsDetected;
    }

    public void setProblemsDetected(boolean problemsDetected) {
        this.problemsDetected = problemsDetected;
    }

    public boolean isSirenTurnedOn() {
        return sirenTurnedOn;
    }

    public void setSirenTurnedOn(boolean sirenTurnedOn) {
        this.sirenTurnedOn = sirenTurnedOn;
    }

    public boolean isSetOffZones() {
        return setOffZones;
    }

    public void setSetOffZones(boolean setOffZones) {
        this.setOffZones = setOffZones;
    }

    public boolean isPanelActivated() {
        return panelActivated;
    }

    public void setPanelActivated(boolean panelActivated) {
        this.panelActivated = panelActivated;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isElectricNetworkFail() {
        return electricNetworkFail;
    }

    public void setElectricNetworkFail(boolean electricNetworkFail) {
        this.electricNetworkFail = electricNetworkFail;
    }

    public boolean isLowBattery() {
        return lowBattery;
    }

    public void setLowBattery(boolean lowBattery) {
        this.lowBattery = lowBattery;
    }

    public boolean isBatteryAbsentOrInverted() {
        return batteryAbsentOrInverted;
    }

    public void setBatteryAbsentOrInverted(boolean batteryAbsentOrInverted) {
        this.batteryAbsentOrInverted = batteryAbsentOrInverted;
    }

    public boolean isShortCircuitedBattery() {
        return shortCircuitedBattery;
    }

    public void setShortCircuitedBattery(boolean shortCircuitedBattery) {
        this.shortCircuitedBattery = shortCircuitedBattery;
    }

    public boolean isAuxiliaryOutputOverload() {
        return auxiliaryOutputOverload;
    }

    public void setAuxiliaryOutputOverload(boolean auxiliaryOutputOverload) {
        this.auxiliaryOutputOverload = auxiliaryOutputOverload;
    }

    public List<Keyboard> getKeyboards() {
        return keyboards;
    }

    public void setKeyboards(List<Keyboard> keyboards) {
        this.keyboards = keyboards;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    public List<Expander> getExpanders() {
        return expanders;
    }

    public void setExpanders(List<Expander> expanders) {
        this.expanders = expanders;
    }

    public BatteryIcon getBatteryIcon() {
        return batteryIcon;
    }

    public void setBatteryIcon(BatteryIcon batteryIcon) {
        this.batteryIcon = batteryIcon;
    }

    public boolean isSirenWireCut() {
        return sirenWireCut;
    }

    public void setSirenWireCut(boolean sirenWireCut) {
        this.sirenWireCut = sirenWireCut;
    }

    public boolean isSirenWireShortCircuited() {
        return sirenWireShortCircuited;
    }

    public void setSirenWireShortCircuited(boolean sirenWireShortCircuited) {
        this.sirenWireShortCircuited = sirenWireShortCircuited;
    }

    public boolean isPhoneLineCut() {
        return phoneLineCut;
    }

    public void setPhoneLineCut(boolean phoneLineCut) {
        this.phoneLineCut = phoneLineCut;
    }

    public boolean isFailureToCommunicateEvent() {
        return failureToCommunicateEvent;
    }

    public void setFailureToCommunicateEvent(boolean failureToCommunicateEvent) {
        this.failureToCommunicateEvent = failureToCommunicateEvent;
    }

    public List<Pgm> getPgmList() {
        return pgmList;
    }

    public void setPgmList(List<Pgm> pgmList) {
        this.pgmList = pgmList;
    }
}
