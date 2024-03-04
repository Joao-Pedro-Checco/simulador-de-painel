package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.arm;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.AckPackage;
import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmProcessorImpl implements ArmProcessor {
    private final PackageSender packageSender;
    private final EventPackageGenerator eventPackageGenerator;

    public ArmProcessorImpl(PackageSender packageSender, EventPackageGenerator eventPackageGenerator) {
        this.packageSender = packageSender;
        this.eventPackageGenerator = eventPackageGenerator;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de arme");
        List<String> bytes = splitBytes(subcommand);

        armPartitions(bytes);
        packageSender.sendPackage(new AckPackage());
    }

    @Override
    public List<String> splitBytes(String subcommand) {
        return Arrays.asList(subcommand.split(" "));
    }

    @Override
    public boolean canProcess(String subcommand) {
        List<String> bytes = splitBytes(subcommand);
        if (bytes.size() > 5) {
            return false;
        }

        return SubcommandIdentifier.getByValue(bytes.get(0)) == SubcommandIdentifier.ARM;
    }

    private void armPartitions(List<String> bytes) {
        EventPackage armEvent = eventPackageGenerator.generateEvent("3401");
        if (bytes.size() == 1) {
            Panel.partitions.forEach(p -> p.setActivated(true));
            packageSender.sendPackage(armEvent);
            return;
        }

        List<Integer> partitions = parsePartitions(bytes.subList(1, bytes.size()));
        partitions.forEach(p -> Panel.partitions.get(p - 1).setActivated(true));
        packageSender.sendPackage(armEvent);
    }

    private List<Integer> parsePartitions(List<String> partitionBytes) {
        return partitionBytes.stream()
                .map(b -> Integer.parseInt(b, 16) - 0x41)
                .collect(Collectors.toList());
    }
}
