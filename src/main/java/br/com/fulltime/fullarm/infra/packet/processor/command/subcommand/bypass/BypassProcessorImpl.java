package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.bypass;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.ack.AckPackage;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

@Service
public class BypassProcessorImpl implements BypassProcessor {
    private final PackageSender packageSender;

    public BypassProcessorImpl(PackageSender packageSender) {
        this.packageSender = packageSender;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de bypass");
        List<String> bytes = splitBytes(subcommand);

        List<String> zonesBytes = bytes.subList(1, bytes.size());
        List<Integer> zones = parseZones(zonesBytes);
        zones.forEach(z -> Panel.getZones().get(z - 1).setBypassed(true));
        packageSender.sendPackage(new AckPackage());
    }

    @Override
    public List<String> splitBytes(String subcommand) {
        return Arrays.asList(subcommand.split(" "));
    }

    @Override
    public boolean canProcess(String subcommand) {
        List<String> bytes = splitBytes(subcommand);
        if (bytes.size() != 9) {
            return false;
        }

        return SubcommandIdentifier.getByValue(bytes.get(0)) == SubcommandIdentifier.BYPASS;
    }

    private List<Integer> parseZones(List<String> bytes) {
        int offset = 1;
        List<Integer> zones = new ArrayList<>();
        for (String b : bytes) {
            BitSet bitSet = hexByteToBitSet(b);
            zones.addAll(offsetZones(bitSet, offset));
            offset += 8;
        }

        return zones;
    }

    private List<Integer> offsetZones(BitSet bitSet, int offset) {
        int index = 0;
        List<Integer> offsetZones = new ArrayList<>();
        while (bitSet.nextSetBit(index) > -1) {
            if (bitSet.get(index)) offsetZones.add(index + offset);
            index++;
        }

        return offsetZones;
    }

    private BitSet hexByteToBitSet(String byteHex) {
        int decimalValue = Integer.parseInt(byteHex, 16);
        return BitSet.valueOf(new long[]{decimalValue});
    }
}
