package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.pgmcontrol;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.ack.AckPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.packet.event.EventPackage;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.listener.PanelStatusListener;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import javafx.application.Platform;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PgmControlProcessorImpl implements PgmControlProcessor {
    private final PackageSender packageSender;
    private final EventPackageGenerator eventPackageGenerator;
    private final PanelStatusListener panelStatusListener;

    public PgmControlProcessorImpl(PackageSender packageSender,
                                   EventPackageGenerator eventPackageGenerator,
                                   PanelStatusListener panelStatusListener) {
        this.packageSender = packageSender;
        this.eventPackageGenerator = eventPackageGenerator;
        this.panelStatusListener = panelStatusListener;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de controle de PGM");
        List<String> bytes = splitBytes(subcommand);
        List<Integer> pgmBytes = parsePgms(bytes.subList(2, bytes.size()));

        String pgmCommand = bytes.get(1);
        if (pgmCommand.equals("4C")) {
            turnPgmsOn(pgmBytes);
            packageSender.sendPackage(new AckPackage());
            updateInterface();
            return;
        }

        turnPgmsOff(pgmBytes);
        packageSender.sendPackage(new AckPackage());
        updateInterface();
    }

    private void updateInterface() {
        if (panelStatusListener != null) {
            Platform.runLater(panelStatusListener::onUpdate);
        }
    }

    @Override
    public List<String> splitBytes(String subcommand) {
        return Arrays.asList(subcommand.split(" "));
    }

    @Override
    public boolean canProcess(String subcommand) {
        List<String> bytes = splitBytes(subcommand);
        if (bytes.size() > 20 || bytes.size() < 2) {
            return false;
        }

        String pgmCommand = bytes.get(1);
        if (!pgmCommand.equals("4C") && !pgmCommand.equals("44")) {
            return false;
        }

        return SubcommandIdentifier.getByValue(bytes.get(0)) == SubcommandIdentifier.PGM_CONTROL;
    }

    private void turnPgmsOn(List<Integer> pgmBytes) {
        pgmBytes.forEach(b -> Panel.getPgmList().get(b - 1).setTurnedOn(true));
        Partition partition = Panel.getPartitions().get(0);
        EventPackage pgmActivationEvent = eventPackageGenerator.generateEvent(EventCode.PGM_ACTIVATION, partition, 0);
        packageSender.sendPackage(pgmActivationEvent);
    }

    private void turnPgmsOff(List<Integer> pgmBytes) {
        pgmBytes.forEach(b -> Panel.getPgmList().get(b - 1).setTurnedOn(false));
        Partition partition = Panel.getPartitions().get(0);
        EventPackage pgmDeactivationEvent = eventPackageGenerator.generateEvent(EventCode.PGM_DEACTIVATION, partition, 0);
        packageSender.sendPackage(pgmDeactivationEvent);
    }

    private List<Integer> parsePgms(List<String> bytes) {
        return bytes.stream().map(this::parsePgmHex).collect(Collectors.toList());
    }

    private int parsePgmHex(String pgmByte) {
        return Integer.parseInt(pgmByte, 16) - 0x30;
    }
}
