package br.com.fulltime.fullarm.infra.packet.parser.event;

import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.PackageType;
import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventPackageParserImpl implements EventPackageParser {
    private final ChecksumGenerator checksumGenerator;

    public EventPackageParserImpl(ChecksumGenerator checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    @Override
    public String parsePackage(GenericPackage genericPackage) {
        EventPackage eventPackage = (EventPackage) genericPackage;
        String connectionType = eventPackage.getConnectionType();
        String account = stringToHex(eventPackage.getAccount());
        String contactId = stringToHex(eventPackage.getContactId());
        String qualifier = stringToHex(eventPackage.getQualifier());
        String eventCode = stringToHex(eventPackage.getEventCode());
        String partition = stringToHex(eventPackage.getPartition());
        String argument = stringToHex(eventPackage.getArgument());

        String hexString = "11B0" + connectionType + account + contactId + qualifier + eventCode + partition + argument;
        List<String> formattedBytes = formatBytes(hexString);
        String finalHexString = parse0AByte(formattedBytes);

        String checksum = checksumGenerator.generateChecksum(finalHexString);
        return (finalHexString + " " + checksum).toUpperCase();
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        if (!(genericPackage instanceof EventPackage)) {
            return false;
        }

        return genericPackage.getPackageType() == PackageType.EVENT;
    }

    private String parse0AByte(List<String> bytes) {
        List<String> handledBytes = bytes.stream()
                .map(b -> b.equals("00") ? "0A" : b).collect(Collectors.toList());
        return String.join(" ", handledBytes);
    }

    private String stringToHex(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char digit : string.toCharArray()) {
            stringBuilder.append("0").append(digit);
        }

        return stringBuilder.toString().trim();
    }

    private List<String> formatBytes(String hexString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            result.append(hexString, i, i + 2);
            result.append(" ");
        }

        return Arrays.asList(result.toString().split(" "));
    }
}
