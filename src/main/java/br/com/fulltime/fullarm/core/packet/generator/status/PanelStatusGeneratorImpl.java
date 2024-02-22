package br.com.fulltime.fullarm.core.packet.generator.status;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.PanelStatus;
import br.com.fulltime.fullarm.core.panel.components.*;
import br.com.fulltime.fullarm.core.panel.Panel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanelStatusGeneratorImpl implements PanelStatusGenerator {
    @Override
    public PanelStatus generateStatus() {
        Logger.log("|============================================================|");
        Logger.log("Gerando pacote de status");
        PanelStatus panelStatus = new PanelStatus();
        Logger.log("Adicionando zonas");
        panelStatus.setZones(Panel.zones);

        Logger.log("Adicionando modelo do painel");
        panelStatus.setPanelModel(Panel.model);

        Logger.log("Adicionando versão do firmware");
        panelStatus.setFirmwareVersion(Panel.firmwareVersion);

        Logger.log("Adicionando status de partição");
        panelStatus.setPartitioned(Panel.partitioned);

        Logger.log("Adicionando partições");
        List<Boolean> activatedPartitions = Panel.partitions.stream().map(Partition::isActivated).collect(Collectors.toList());
        panelStatus.setActivatedPartitions(activatedPartitions);

        Logger.log("Adicionando sirene ligada");
        boolean sirenIsOn = Panel.siren.isTurnedOn();
        panelStatus.setSirenTurnedOn(sirenIsOn);

        Logger.log("Adicionando zonas em disparo");
        boolean setOffZones = Panel.zones.stream().anyMatch(Zone::isSetOff);
        panelStatus.setSetOffZones(setOffZones);

        Logger.log("Adicionando painel ativado");
        boolean panelIsActivated = Panel.activated;
        panelStatus.setPanelActivated(panelIsActivated);

        Logger.log("Adicionando problemas detectados");
        boolean problemsDetected = sirenIsOn || setOffZones || panelIsActivated;
        panelStatus.setProblemsDetected(problemsDetected);

        Logger.log("Adicionando data e hora");
        panelStatus.setDateTime(LocalDateTime.now());

        Logger.log("Adicionando falha na rede elétrica");
        boolean electricNetworkFail = false;
        panelStatus.setElectricNetworkFail(electricNetworkFail);

        Logger.log("Adicionando bateria baixa");
        boolean lowBattery = false;
        panelStatus.setLowBattery(lowBattery);

        Logger.log("Adicionando bateria ausente ou invertida");
        boolean batteryAbsentOrInverted = false;
        panelStatus.setBatteryAbsentOrInverted(batteryAbsentOrInverted);

        Logger.log("Adicionando bateria com curto circuito");
        boolean shortCircuitedBattery = false;
        panelStatus.setShortCircuitedBattery(shortCircuitedBattery);

        Logger.log("Adicionando sobrecarga na saída auxiliar");
        boolean auxiliaryOutputOverload = false;
        panelStatus.setAuxiliaryOutputOverload(auxiliaryOutputOverload);

        Logger.log("Adicionando teclados");
        List<Keyboard> keyboards = Panel.keyboards;
        panelStatus.setKeyboards(keyboards);

        Logger.log("Adicionando receptores");
        List<Receiver> receivers = Panel.receivers;
        panelStatus.setReceivers(receivers);

        Logger.log("Adicionando expansores");
        panelStatus.setExpanders(Panel.expanders);

        Logger.log("Adicionando bateria");
        panelStatus.setBatteryIcon(Panel.batteryIcon);

        Logger.log("Adicionando corte no fio da sirene");
        boolean sirenWireCut = false;
        panelStatus.setSirenWireCut(sirenWireCut);

        Logger.log("Adicionando curto circuito no fio da sirene");
        boolean sirenWireShortCircuited = false;
        panelStatus.setSirenWireShortCircuited(sirenWireShortCircuited);

        Logger.log("Adicionando corte na linha telefônica");
        boolean phoneLineCut = false;
        panelStatus.setPhoneLineCut(phoneLineCut);

        Logger.log("Adicionando falha ao comunicar evento");
        boolean failureToCommunicateEvent = false;
        panelStatus.setFailureToCommunicateEvent(failureToCommunicateEvent);

        Logger.log("Adicionando pgms");
        panelStatus.setPgmList(Panel.pgmList);
        Logger.log("|============================================================|");

        return panelStatus;
    }
}
