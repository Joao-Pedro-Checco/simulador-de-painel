package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import br.com.fulltime.fullarm.infra.ChecksumGeneratorImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPackageGeneratorImpl implements AuthenticationPackageGenerator {
    private final ChecksumGenerator checksumGenerator;

    public AuthenticationPackageGeneratorImpl(ChecksumGeneratorImpl checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    @Override
    public String generatePackage(String connectionType, String account, String macAddress) {
        StringBuilder splitPackage = splitBytes("0794" + connectionType + account + macAddress);
        byte checksum = checksumGenerator.generateChecksum(splitPackage.toString());

        String checksumToString = Integer.toHexString(checksum);
        splitPackage.append(checksumToString);

        return splitPackage.toString().trim();
    }

    private StringBuilder splitBytes(String hexString) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < hexString.length(); i += 2) {
            result.append(hexString, i, i + 2);
            result.append(" ");
        }

        return result;
    }
}
