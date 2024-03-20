package br.com.fulltime.fullarm.core.panel.handler.zone;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

@Service
public class ZoneHandlerImpl implements ZoneHandler {
    private final EventPackageGenerator eventPackageGenerator;
    private final PackageSender packageSender;

    public ZoneHandlerImpl(EventPackageGenerator eventPackageGenerator, PackageSender packageSender) {
        this.eventPackageGenerator = eventPackageGenerator;
        this.packageSender = packageSender;
    }

    @Override
    public void toggleZoneOpenState(Zone zone) {
        String logMessage = (zone.isOpen() ? "Fechando" : "Abrindo") + " setor " + zone.getZoneNumber();
        Logger.log(logMessage);

        zone.setOpen(!zone.isOpen());
        if (Panel.isArmed() && !zone.isBypassed()) {
            zone.setMemory(true);
            EventCode eventCode = zone.isOpen() ? EventCode.BURGLARY_ALARM :  EventCode.ALARM_RESTORE;
            sendEvent(eventCode, zone);
        }

    }

    @Override
    public void toggleZoneBypassState(Zone zone) {
        String logMessage = (zone.isBypassed() ? "Desinibindo" : "Inibindo") + " setor " + zone.getZoneNumber();
        Logger.log(logMessage);

        zone.setBypassed(!zone.isBypassed());
    }

    @Override
    public void toggleZoneTamperState(Zone zone) {
        String logMessage = (zone.isTampered() ? "Restaurando " : "") + "Tamper no setor " + zone.getZoneNumber();
        Logger.log(logMessage);

        zone.setTampered(!zone.isTampered());
        EventCode eventCode = zone.isTampered() ? EventCode.TAMPER : EventCode.TAMPER_RESTORE;
        sendEvent(eventCode, zone);
    }

    @Override
    public void toggleShortCircuitState(Zone zone) {
        String logMessage = (zone.isShortCircuit() ? "Restaurando " : "") + "Curto-circuito no setor " + zone.getZoneNumber();
        Logger.log(logMessage);

        zone.setShortCircuit(!zone.isShortCircuit());
        EventCode eventCode = zone.isShortCircuit() ? EventCode.SHORT_CIRCUIT : EventCode.SHORT_CIRCUIT_RESTORE;
        sendEvent(eventCode, zone);
    }

    @Override
    public void toggleLowBatteryState(Zone zone) {
        boolean isBatteryLow = zone.isBatteryLowOnWirelessSensor();
        String logMessage = (isBatteryLow ? "Restaurando " : "") + "Bateria fraca no setor " + zone.getZoneNumber();
        Logger.log(logMessage);

        zone.setBatteryLowOnWirelessSensor(!isBatteryLow);
        EventCode eventCode = zone.isBatteryLowOnWirelessSensor() ? EventCode.LOW_BATTERY : EventCode.LOW_BATTERY_RESTORE;
        sendEvent(eventCode, zone);
    }

    private void sendEvent(EventCode eventCode, Zone zone) {
        Partition partition = zone.getPartition();
        int argument = zone.getZoneNumber();
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode, partition, argument);
        packageSender.sendPackage(eventPackage);
    }
}
