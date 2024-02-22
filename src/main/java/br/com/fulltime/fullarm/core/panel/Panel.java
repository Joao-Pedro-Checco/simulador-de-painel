package br.com.fulltime.fullarm.core.panel;

import br.com.fulltime.fullarm.core.panel.components.*;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    public static final String model = "41";
    public static final String firmwareVersion = "52";
    public static boolean connected;
    public static boolean armed;
    public static boolean partitioned;
    public static boolean activated;
    public static Siren siren;
    public static BatteryIcon batteryIcon;
    public static final List<Zone> zones = new ArrayList<>();
    public static final List<Partition> partitions = new ArrayList<>();
    public static final List<Keyboard> keyboards = new ArrayList<>();
    public static final List<Receiver> receivers = new ArrayList<>();
    public static final List<Expander> expanders = new ArrayList<>();
    public static final List<Pgm> pgmList = new ArrayList<>();
}
