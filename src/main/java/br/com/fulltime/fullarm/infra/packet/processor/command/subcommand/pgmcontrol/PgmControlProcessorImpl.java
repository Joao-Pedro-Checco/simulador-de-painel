package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.pgmcontrol;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.AckPackage;
import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PgmControlProcessorImpl implements PgmControlProcessor {
    private final PackageSender packageSender;
    private final EventPackageGenerator eventPackageGenerator;

    public PgmControlProcessorImpl(PackageSender packageSender, EventPackageGenerator eventPackageGenerator) {
        this.packageSender = packageSender;
        this.eventPackageGenerator = eventPackageGenerator;
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
            return;
        }

        turnPgmsOff(pgmBytes);
        packageSender.sendPackage(new AckPackage());
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
        EventPackage pgmActivationEvent = eventPackageGenerator.generateEvent(EventCode.PGM_ACTIVATION);
        pgmBytes.forEach(b -> Panel.getPgmList().get(b - 1).setTurnedOn(true));
        packageSender.sendPackage(pgmActivationEvent);
    }

    private void turnPgmsOff(List<Integer> pgmBytes) {
        EventPackage pgmDeactivationEvent = eventPackageGenerator.generateEvent(EventCode.PGM_DEACTIVATION);
        pgmBytes.forEach(b -> Panel.getPgmList().get(b - 1).setTurnedOn(false));
        packageSender.sendPackage(pgmDeactivationEvent);
    }

    private List<Integer> parsePgms(List<String> bytes) {
        return bytes.stream().map(this::parsePgmHex).collect(Collectors.toList());
    }

    private int parsePgmHex(String pgmByte) {
        return Integer.parseInt(pgmByte, 16) - 0x30;
    }
}
