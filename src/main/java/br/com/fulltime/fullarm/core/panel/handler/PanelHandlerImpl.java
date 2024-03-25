package br.com.fulltime.fullarm.core.panel.handler;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.event.EventPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Pgm;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanelHandlerImpl implements PanelHandler {
    private final EventPackageGenerator eventPackageGenerator;
    private final PackageSender packageSender;

    public PanelHandlerImpl(EventPackageGenerator eventPackageGenerator, PackageSender packageSender) {
        this.eventPackageGenerator = eventPackageGenerator;
        this.packageSender = packageSender;
    }

    @Override
    public void armPanel() {
        boolean anyZoneIsOpen = Panel.getZones().stream().filter(Zone::isEnabled).anyMatch(Zone::isOpen);
        if (!Panel.isCanArmWithOpenZones() && anyZoneIsOpen) {
            return;
        }

        Logger.log("Armando painel");
        Panel.setArmed(true);
        Panel.getPartitions().forEach(p -> p.setActivated(true));
        Partition partition = Panel.getPartitions().get(0);
        sendEvent(EventCode.ARM, partition, 0);

        List<Zone> openZones = Panel.getZones().stream()
                .filter(Zone::isEnabled)
                .filter(Zone::isOpen)
                .collect(Collectors.toList());
        if (!openZones.isEmpty()) {
            openZones.forEach(z -> sendEvent(EventCode.BURGLARY_ALARM, partition, z.getZoneNumber()));
        }
    }

    @Override
    public void disarmPanel() {
        Logger.log("Desarmando painel");
        Panel.setArmed(false);
        Panel.getZones().forEach(z -> z.setBypassed(false));
        Panel.getPartitions().forEach(p -> p.setActivated(false));
        sendEvent(EventCode.DISARM, Panel.getPartitions().get(0), 0);
    }

    @Override
    public void partitionPanel() {
        Logger.log("Particionando painel");
        Panel.setPartitioned(true);
    }

    @Override
    public void unPartitionPanel() {
        Logger.log("Desparticionando o painel");
        Panel.setPartitioned(false);
    }

    @Override
    public void armPartition(Partition partition) {
        List<Zone> zones = partition.getZones();
        boolean anyZoneIsOpen = zones.stream().anyMatch(Zone::isOpen);
        if (!Panel.isCanArmWithOpenZones() && anyZoneIsOpen) {
            return;
        }

        Logger.log("Armando Partição " + partition.getPartitionNumber());
        zones.forEach(z -> z.setMemory(false));

        boolean anyPartitionIsArmed = Panel.getPartitions().stream().anyMatch(Partition::isActivated);
        if (!anyPartitionIsArmed) {
            Panel.setArmed(true);
        }

        partition.setActivated(true);
        sendEvent(EventCode.ARM, partition, 0);

        List<Zone> openZones = zones.stream().filter(Zone::isOpen).collect(Collectors.toList());
        if (!openZones.isEmpty()) {
            openZones.forEach(z -> sendEvent(EventCode.BURGLARY_ALARM, partition, z.getZoneNumber()));
        }
    }

    @Override
    public void disarmPartition(Partition partition) {
        Logger.log("Desarmando Partição " + partition.getPartitionNumber());
        List<Partition> activatedPartitions = Panel.getPartitions().stream().filter(Partition::isActivated).collect(Collectors.toList());
        if (activatedPartitions.size() == 1) {
            Panel.setArmed(false);
        }

        partition.setActivated(false);
        partition.getZones().forEach(z -> z.setBypassed(false));
        sendEvent(EventCode.DISARM, partition, 0);
    }

    @Override
    public void turnPgmOn(Pgm pgm) {
        Logger.log("Ligando PGM " + pgm.getPgmNumber());
        pgm.setTurnedOn(true);
        sendEvent(EventCode.PGM_ACTIVATION, Panel.getPartitions().get(0), pgm.getPgmNumber());
    }

    @Override
    public void turnPgmOff(Pgm pgm) {
        Logger.log("Desligando PGM " + pgm.getPgmNumber());
        pgm.setTurnedOn(false);
        sendEvent(EventCode.PGM_DEACTIVATION, Panel.getPartitions().get(0), pgm.getPgmNumber());
    }

    private void sendEvent(EventCode eventCode, Partition partition, Integer argument) {
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode, partition, argument);
        packageSender.sendPackage(eventPackage);
    }
}
