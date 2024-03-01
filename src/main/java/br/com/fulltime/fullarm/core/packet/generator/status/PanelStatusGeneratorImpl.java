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
        Logger.log("|============================================================|");
        Logger.log("Gerando pacote de status");
        StatusPackage statusPackage = new StatusPackage();
        Logger.log("Adicionando zonas");
        statusPackage.setZones(Panel.zones);

        Logger.log("Adicionando modelo do painel");
        statusPackage.setPanelModel(Panel.model.getModelByte());

        Logger.log("Adicionando versão do firmware");
        String firmwareVersion = "52";
        statusPackage.setFirmwareVersion(firmwareVersion);

        Logger.log("Adicionando status de partição");
        statusPackage.setPartitioned(Panel.partitioned);

        Logger.log("Adicionando partições");
        List<Boolean> activatedPartitions = Panel.partitions.stream().map(Partition::isActivated).collect(Collectors.toList());
        statusPackage.setActivatedPartitions(activatedPartitions);

        Logger.log("Adicionando sirene ligada");
        Panel.siren = new Siren();
        Panel.siren.setTurnedOn(false);
        boolean sirenIsOn = Panel.siren.isTurnedOn();
        statusPackage.setSirenTurnedOn(sirenIsOn);

        Logger.log("Adicionando zonas em disparo");
        boolean burgledZones = Panel.zones.stream().anyMatch(Zone::isViolated);
        statusPackage.setBurgledZones(burgledZones);

        Logger.log("Adicionando painel ativado");
        boolean panelIsArmed = Panel.armed;
        statusPackage.setPanelArmed(panelIsArmed);

        Logger.log("Adicionando problemas detectados");
        boolean problemsDetected = sirenIsOn || burgledZones || panelIsArmed;
        statusPackage.setProblemsDetected(problemsDetected);

        Logger.log("Adicionando data e hora");
        statusPackage.setDateTime(LocalDateTime.now());

        Logger.log("Adicionando falha na rede elétrica");
        boolean electricNetworkFail = false;
        statusPackage.setElectricNetworkFail(electricNetworkFail);

        Logger.log("Adicionando bateria baixa");
        boolean lowBattery = false;
        statusPackage.setLowBattery(lowBattery);

        Logger.log("Adicionando bateria ausente ou invertida");
        boolean batteryAbsentOrInverted = false;
        statusPackage.setBatteryAbsentOrInverted(batteryAbsentOrInverted);

        Logger.log("Adicionando bateria com curto circuito");
        boolean shortCircuitedBattery = false;
        statusPackage.setShortCircuitedBattery(shortCircuitedBattery);

        Logger.log("Adicionando sobrecarga na saída auxiliar");
        boolean auxiliaryOutputOverload = false;
        statusPackage.setAuxiliaryOutputOverload(auxiliaryOutputOverload);

        Logger.log("Adicionando teclados");
        statusPackage.setKeyboards(keyboards);

        Logger.log("Adicionando receptores");
        statusPackage.setReceivers(receivers);

        Logger.log("Adicionando expansores de pgm");
        statusPackage.setPgmExpanders(pgmExpanders);

        Logger.log("Adicionando expansores de zona");
        statusPackage.setZoneExpanders(zoneExpanders);

        Logger.log("Adicionando bateria");
        Panel.battery = new Battery();
        Panel.battery.setBatteryOutlineOn(true);
        Panel.battery.setLevelOneCellOn(true);
        Panel.battery.setLevelTwoCellOn(true);
        Panel.battery.setLevelThreeCellOn(true);
        Panel.battery.setBatteryOutlineBlink(false);
        Panel.battery.setLevelOneCellBlink(false);
        Panel.battery.setLevelTwoCellBlink(false);
        Panel.battery.setLevelThreeCellBlink(false);
        statusPackage.setBattery(Panel.battery);

        Logger.log("Adicionando corte no fio da sirene");
        boolean sirenWireCut = false;
        statusPackage.setSirenWireCut(sirenWireCut);

        Logger.log("Adicionando curto circuito no fio da sirene");
        boolean sirenWireShortCircuited = false;
        statusPackage.setSirenWireShortCircuited(sirenWireShortCircuited);

        Logger.log("Adicionando corte na linha telefônica");
        boolean phoneLineCut = false;
        statusPackage.setPhoneLineCut(phoneLineCut);

        Logger.log("Adicionando falha ao comunicar evento");
        boolean failureToCommunicateEvent = false;
        statusPackage.setFailureToCommunicateEvent(failureToCommunicateEvent);

        Logger.log("Adicionando pgms");
        statusPackage.setPgmList(Panel.pgmList);
        Logger.log("|============================================================|");

        return statusPackage;
    }
}
