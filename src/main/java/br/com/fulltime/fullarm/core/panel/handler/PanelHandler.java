package br.com.fulltime.fullarm.core.panel.handler;

import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Pgm;

public interface PanelHandler {
    void armPanel();
    void disarmPanel();
    void partitionPanel();
    void unPartitionPanel();
    void armPartition(Partition partition);
    void disarmPartition(Partition partition);
    void turnPgmOn(Pgm pgm);
    void turnPgmOff(Pgm pgm);
}
