package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventPackageGeneratorImpl implements EventPackageGenerator {
    private final ChecksumGenerator checksumGenerator;

    public EventPackageGeneratorImpl(ChecksumGenerator checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    @Override
    public String generateEvent(String connectionType, String account, String eventCode, String partition) {
        String hexAccount = stringToHex(account);
        String hexEventCode = stringToHex(eventCode);
        String hexPartition = stringToHex(partition);

        String hexString = "11B0" + connectionType + hexAccount + "0108" + hexEventCode + hexPartition + "0A0A01";
        List<String> splitPackage = splitBytes(hexString);

        String handledBytes = handleByte0A(splitPackage);
        byte checksum = checksumGenerator.generateChecksum(handledBytes);

        String checksumToString = Integer.toHexString(checksum).toUpperCase();
        return handledBytes + " " + checksumToString;
    }

    private String stringToHex(String string) {
        StringBuilder newString = new StringBuilder();
        for (char digit : string.toCharArray()) {
            newString.append("0").append(digit);
        }

        return newString.toString().trim();
    }

    private String handleByte0A(List<String> bytes) {
        List<String> handledBytes = bytes.stream()
                .map(b -> b.equals("00") ? "0A" : b).collect(Collectors.toList());
        return String.join(" ", handledBytes);
    }

    private List<String> splitBytes(String hexString) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < hexString.length(); i += 2) {
            result.append(hexString, i, i + 2);
            result.append(" ");
        }

        return Arrays.asList(result.toString().split(" "));
    }
}
