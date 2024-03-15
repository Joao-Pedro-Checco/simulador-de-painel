package br.com.fulltime.fullarm.core.panel;

import br.com.fulltime.fullarm.core.panel.components.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Panel {
    private static ConnectionType connectionType;
    private static String account;
    private static String password;
    private static final PanelModel model = PanelModel.AMT4010SMART;
    private static boolean connected;
    private static boolean armed;
    private static boolean isAuthenticated;
    private static boolean partitioned;
    private static Siren siren;
    private static Battery battery;
    private static final List<Partition> partitions = new ArrayList<Partition>(){{
        for (int i = 0; i < 4; i++) {
            Partition partition = new Partition();
            partition.setPartitionNumber(i + 1);
            partition.setActivated(false);
            add(partition);
        }
    }};
    private static final List<Zone> zones = new ArrayList<Zone>(){{
        for (int i = 0; i < 64; i++) {
            Zone zone = new Zone();
            zone.setZoneNumber(i + 1);
            zone.setOpen(false);
            zone.setViolated(false);
            zone.setPartition(Panel.getPartitions().get(0));
            add(zone);
        }
    }};
    private static final List<Pgm> pgmList = new ArrayList<Pgm>(){{
        for (int i = 0; i < 19; i++) {
            Pgm pgm = new Pgm();
            pgm.setPgmNumber(i + 1);
            pgm.setTurnedOn(false);
            add(pgm);
        }
    }};

    public static ConnectionType getConnectionType() {
        return connectionType;
    }

    public static void setConnectionType(ConnectionType connectionType) {
        Panel.connectionType = connectionType;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        Panel.account = account;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Panel.password = password;
    }

    public static PanelModel getModel() {
        return model;
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void setConnected(boolean connected) {
        Panel.connected = connected;
    }

    public static boolean isArmed() {
        return armed;
    }

    public static void setArmed(boolean armed) {
        Panel.armed = armed;
    }

    public static boolean isIsAuthenticated() {
        return isAuthenticated;
    }

    public static void setIsAuthenticated(boolean isAuthenticated) {
        Panel.isAuthenticated = isAuthenticated;
    }

    public static boolean isPartitioned() {
        return partitioned;
    }

    public static void setPartitioned(boolean partitioned) {
        Panel.partitioned = partitioned;
    }

    public static Siren getSiren() {
        return siren;
    }

    public static void setSiren(Siren siren) {
        Panel.siren = siren;
    }

    public static Battery getBattery() {
        return battery;
    }

    public static void setBattery(Battery battery) {
        Panel.battery = battery;
    }

    public static List<Zone> getZones() {
        return zones;
    }

    public static List<Partition> getPartitions() {
        return partitions;
    }

    public static List<Pgm> getPgmList() {
        return pgmList;
    }
}
