package br.com.fulltime.fullarm.core.panel.handler;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanelHandlerImpl implements PanelHandler {
    private final EventPackageGenerator eventPackageGenerator;
    private final PackageSender packageSender;
    private boolean canArmWithOpenZones;

    public PanelHandlerImpl(EventPackageGenerator eventPackageGenerator, PackageSender packageSender) {
        this.eventPackageGenerator = eventPackageGenerator;
        this.packageSender = packageSender;
    }

    @Override
    public void armPanel() {
        List<Zone> openZones = Panel.getZones().stream().filter(Zone::isOpen).collect(Collectors.toList());
        if (!canArmWithOpenZones && !openZones.isEmpty()) {
            return;
        }

        Logger.log("Armando painel");
        Panel.setArmed(true);
        Panel.getPartitions().forEach(p -> p.setActivated(true));
        Partition partition = Panel.getPartitions().get(0);
        sendEvent(EventCode.ARM, partition, 0);

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
        List<Zone> openZones = partition.getZones().stream().filter(Zone::isOpen).collect(Collectors.toList());
        if (!canArmWithOpenZones && !openZones.isEmpty()) {
            return;
        }

        Logger.log("Armando Partição " + partition.getPartitionNumber());
        Panel.setArmed(true);
        partition.setActivated(true);
        sendEvent(EventCode.ARM, partition, 0);

        if (!openZones.isEmpty()) {
            openZones.forEach(z -> sendEvent(EventCode.BURGLARY_ALARM, partition, z.getZoneNumber()));
        }
    }

    @Override
    public void disarmPartition(Partition partition) {
        Logger.log("Desarmando Partição " + partition.getPartitionNumber());
        Panel.setArmed(false);
        partition.setActivated(false);
        sendEvent(EventCode.DISARM, partition, 0);
    }

    @Override
    public void openZone(Zone zone) {
        Logger.log("Abrindo zona " + zone.getZoneNumber());
        zone.setOpen(true);

        if (!zone.isBypassed()) {
            Partition partition = zone.getPartition();
            int argument = zone.getZoneNumber();
            sendEvent(EventCode.BURGLARY_ALARM, partition, argument);
        }
    }

    @Override
    public void closeZone(Zone zone) {
        Logger.log("Fechando zona " + zone.getZoneNumber());
        zone.setOpen(false);

        if (!zone.isBypassed()) {
            Partition partition = zone.getPartition();
            int argument = zone.getZoneNumber();
            sendEvent(EventCode.ALARM_RESTORE, partition, argument);
        }
    }

    @Override
    public void setCanArmWithOpenZones(boolean canArm) {
        Logger.log("Permissão para armar partição com zonas abertas: " + canArm);
        this.canArmWithOpenZones = canArm;
    }

    private void sendEvent(EventCode eventCode, Partition partition, Integer argument) {
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode, partition, argument);
        packageSender.sendPackage(eventPackage);
    }
}
