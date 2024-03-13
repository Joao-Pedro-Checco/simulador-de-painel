package br.com.fulltime.fullarm.core.panel.handler;

import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Zone;

public interface PanelHandler {
    void armPanel();
    void disarmPanel();
    void partitionPanel();
    void unPartitionPanel();
    void armPartition(Partition partition);
    void disarmPartition(Partition partition);
    void openZone(Zone zone);
    void closeZone(Zone zone);
    void setCanArmWithOpenZones(boolean canArm);
}
