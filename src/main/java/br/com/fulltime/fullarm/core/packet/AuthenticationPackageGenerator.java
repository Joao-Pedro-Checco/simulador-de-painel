package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import br.com.fulltime.fullarm.infra.ChecksumGeneratorImpl;
import br.com.fulltime.fullarm.infra.HexStringConverter;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPackageGenerator {
    private final ChecksumGenerator checksumGenerator;

    public AuthenticationPackageGenerator(ChecksumGeneratorImpl checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    public byte[] generatePackage(String connectionType, String account, String macAddress) {
        String packet = splitBytes("0794" + connectionType + account + macAddress);
        return HexStringConverter.hexStringToByteArray(packet);
    }

    private String splitBytes(String hexString) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < hexString.length(); i += 2) {
            result.append(hexString, i, i + 2);
            result.append(" ");
        }

        String checksum = Integer.toHexString(checksumGenerator.generateChecksum(result.toString()));
        result.append(checksum);

        return result.toString().trim();
    }
}
