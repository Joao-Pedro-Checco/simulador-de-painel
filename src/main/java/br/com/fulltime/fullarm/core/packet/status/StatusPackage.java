package br.com.fulltime.fullarm.core.packet.status;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.core.panel.components.*;

import java.time.LocalDateTime;
import java.util.List;

public class StatusPackage extends GenericPackage {
    private List<Zone> zones;
    private String panelModel;
    private String firmwareVersion;
    private boolean partitioned;
    private List<Boolean> activatedPartitions;
    private boolean problemsDetected;
    private boolean sirenTurnedOn;
    private boolean burgledZones;
    private boolean panelArmed;
    private LocalDateTime dateTime;
    private boolean electricNetworkFail;
    private boolean lowBattery;
    private boolean batteryAbsentOrInverted;
    private boolean shortCircuitedBattery;
    private boolean auxiliaryOutputOverload;
    private List<Keyboard> keyboards;
    private List<Receiver> receivers;
    private List<Expander> pgmExpanders;
    private List<Expander> zoneExpanders;
    private Battery battery;
    private boolean sirenWireCut;
    private boolean sirenWireShortCircuited;
    private boolean phoneLineCut;
    private boolean failureToCommunicateEvent;
    private List<Pgm> pgmList;

    public StatusPackage() {
        super(PackageType.STATUS);
    }

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

    public boolean isBurgledZones() {
        return burgledZones;
    }

    public void setBurgledZones(boolean burgledZones) {
        this.burgledZones = burgledZones;
    }

    public boolean isPanelArmed() {
        return panelArmed;
    }

    public void setPanelArmed(boolean panelArmed) {
        this.panelArmed = panelArmed;
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

    public List<Expander> getPgmExpanders() {
        return pgmExpanders;
    }

    public void setPgmExpanders(List<Expander> pgmExpanders) {
        this.pgmExpanders = pgmExpanders;
    }

    public List<Expander> getZoneExpanders() {
        return zoneExpanders;
    }

    public void setZoneExpanders(List<Expander> zoneExpanders) {
        this.zoneExpanders = zoneExpanders;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
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
