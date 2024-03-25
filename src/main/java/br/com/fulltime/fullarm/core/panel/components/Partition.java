package br.com.fulltime.fullarm.core.panel.components;

import br.com.fulltime.fullarm.core.panel.Panel;

import java.util.List;
import java.util.stream.Collectors;

public class Partition {
    private int partitionNumber;
    private boolean activated;

    public int getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(int partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<Zone> getZones() {
        return Panel.getZones().stream()
                .filter(z -> z.getPartition().getPartitionNumber() == this.partitionNumber)
                .collect(Collectors.toList());
    }
}
