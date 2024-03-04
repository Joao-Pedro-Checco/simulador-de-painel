package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.disarm;

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
public class DisarmProcessorImpl implements DisarmProcessor {
    private final PackageSender packageSender;
    private final EventPackageGenerator eventPackageGenerator;

    public DisarmProcessorImpl(PackageSender packageSender, EventPackageGenerator eventPackageGenerator) {
        this.packageSender = packageSender;
        this.eventPackageGenerator = eventPackageGenerator;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de desarme");
        List<String> bytes = splitBytes(subcommand);

        unBypassZones(bytes);
        disarmPartitions(bytes);
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

        return SubcommandIdentifier.getByValue(bytes.get(0)) == SubcommandIdentifier.DISARM;
    }

    private void disarmPartitions(List<String> bytes) {
        EventPackage disarmEvent = eventPackageGenerator.generateEvent("1407");
        if (bytes.size() == 1) {
            Panel.getPartitions().forEach(p -> p.setActivated(false));
            packageSender.sendPackage(disarmEvent);
            return;
        }

        List<Integer> partitions = parsePartitions(bytes.subList(1, bytes.size()));
        partitions.forEach(p -> Panel.getPartitions().get(p - 1).setActivated(false));
        packageSender.sendPackage(disarmEvent);
    }

    private List<Integer> parsePartitions(List<String> partitionBytes) {
        return partitionBytes.stream()
                .map(b -> Integer.parseInt(b, 16) - 0x41)
                .collect(Collectors.toList());
    }

    private void unBypassZones(List<String> bytes) {
        if (!Panel.isPartitioned()) {
            Panel.getZones().forEach(z -> z.setBypassed(false));
            return;
        }

        List<Integer> partitions = parsePartitions(bytes.subList(1, bytes.size()));
        Panel.getZones().forEach(z -> {
            if (partitions.contains(z.getPartition())) {
                z.setBypassed(false);
            }
        });
    }
}
