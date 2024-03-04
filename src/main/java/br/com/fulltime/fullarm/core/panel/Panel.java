package br.com.fulltime.fullarm.core.panel;

import br.com.fulltime.fullarm.core.panel.components.*;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    public static ConnectionType connectionType;
    public static String account;
    public static final PanelModel model = PanelModel.AMT4010SMART;
    public static boolean connected;
    public static boolean armed;
    public static boolean isAuthenticated;
    public static boolean partitioned;
    public static Siren siren;
    public static Battery battery;
    public static final List<Zone> zones = new ArrayList<Zone>(){{
        for (int i = 0; i < 64; i++) {
            Zone zone = new Zone();
            zone.setZoneNumber(i + 1);
            zone.setOpen(false);
            zone.setViolated(false);
            zone.setBypassed(false);
            add(zone);
        }
    }};
    public static final List<Partition> partitions = new ArrayList<Partition>(){{
        for (int i = 0; i < 4; i++) {
            Partition partition = new Partition();
            partition.setPartitionNumber(i + 1);
            partition.setActivated(false);
            add(partition);
        }
    }};
    public static final List<Pgm> pgmList = new ArrayList<Pgm>(){{
        for (int i = 0; i < 19; i++) {
            Pgm pgm = new Pgm();
            pgm.setPgmNumber(i + 1);
            pgm.setTurnedOn(false);
            add(pgm);
        }
    }};
}
