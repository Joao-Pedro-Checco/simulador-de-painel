package br.com.fulltime.fullarm.core.packet.generator.status;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.StatusPackage;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanelStatusGeneratorImpl implements PanelStatusGenerator {
    private final List<Keyboard> keyboards = new ArrayList<Keyboard>() {{
        for (int i = 0; i < 4; i++) {
            Keyboard keyboard = new Keyboard();
            keyboard.setProblem(false);
            keyboard.setTamper(false);
            add(keyboard);
        }
    }};
    private final List<Receiver> receivers = new ArrayList<Receiver>() {{
        for (int i = 0; i < 4; i++) {
            Receiver receiver = new Receiver();
            receiver.setProblem(false);
            add(receiver);
        }
    }};
    private final List<Expander> pgmExpanders = new ArrayList<Expander>() {{
        for (int i = 0; i < 4; i++) {
            Expander expander = new Expander();
            expander.setProblem(false);
            add(expander);
        }
    }};
    private final List<Expander> zoneExpanders = new ArrayList<Expander>() {{
        for (int i = 0; i < 6; i++) {
            Expander expander = new Expander();
            expander.setProblem(false);
            add(expander);
        }
    }};

    @Override
    public StatusPackage generateStatus() {
        Logger.log("Gerando pacote de status");
        StatusPackage statusPackage = new StatusPackage();
        statusPackage.setZones(Panel.getZones());

        statusPackage.setPanelModel(Panel.getModel().getModelByte());

        String firmwareVersion = "52";
        statusPackage.setFirmwareVersion(firmwareVersion);

        statusPackage.setPartitioned(Panel.isPartitioned());

        List<Boolean> activatedPartitions = Panel.getPartitions().stream().map(Partition::isActivated).collect(Collectors.toList());
        statusPackage.setActivatedPartitions(activatedPartitions);

        Panel.setSiren(new Siren());
        Panel.getSiren().setTurnedOn(false);
        boolean sirenIsOn = Panel.getSiren().isTurnedOn();
        statusPackage.setSirenTurnedOn(sirenIsOn);

        boolean burgledZones = Panel.getZones().stream().anyMatch(Zone::isViolated);
        statusPackage.setBurgledZones(burgledZones);

        boolean panelIsArmed = Panel.isArmed();
        statusPackage.setPanelArmed(panelIsArmed);

        boolean problemsDetected = sirenIsOn || burgledZones || panelIsArmed;
        statusPackage.setProblemsDetected(problemsDetected);

        statusPackage.setDateTime(LocalDateTime.now());

        boolean electricNetworkFail = false;
        statusPackage.setElectricNetworkFail(electricNetworkFail);

        boolean lowBattery = false;
        statusPackage.setLowBattery(lowBattery);

        boolean batteryAbsentOrInverted = false;
        statusPackage.setBatteryAbsentOrInverted(batteryAbsentOrInverted);

        boolean shortCircuitedBattery = false;
        statusPackage.setShortCircuitedBattery(shortCircuitedBattery);

        boolean auxiliaryOutputOverload = false;
        statusPackage.setAuxiliaryOutputOverload(auxiliaryOutputOverload);

        statusPackage.setKeyboards(keyboards);

        statusPackage.setReceivers(receivers);

        statusPackage.setPgmExpanders(pgmExpanders);

        statusPackage.setZoneExpanders(zoneExpanders);

        Panel.setBattery(new Battery());
        Panel.getBattery().setBatteryOutlineOn(true);
        Panel.getBattery().setLevelOneCellOn(true);
        Panel.getBattery().setLevelTwoCellOn(true);
        Panel.getBattery().setLevelThreeCellOn(true);
        Panel.getBattery().setBatteryOutlineBlink(false);
        Panel.getBattery().setLevelOneCellBlink(false);
        Panel.getBattery().setLevelTwoCellBlink(false);
        Panel.getBattery().setLevelThreeCellBlink(false);
        statusPackage.setBattery(Panel.getBattery());

        boolean sirenWireCut = false;
        statusPackage.setSirenWireCut(sirenWireCut);

        boolean sirenWireShortCircuited = false;
        statusPackage.setSirenWireShortCircuited(sirenWireShortCircuited);

        boolean phoneLineCut = false;
        statusPackage.setPhoneLineCut(phoneLineCut);

        boolean failureToCommunicateEvent = false;
        statusPackage.setFailureToCommunicateEvent(failureToCommunicateEvent);

        statusPackage.setPgmList(Panel.getPgmList());

        return statusPackage;
    }
}
