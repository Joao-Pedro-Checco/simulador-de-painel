package br.com.fulltime.fullarm.infra.packet.parser.authentication;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPackageParserImpl implements AuthenticationPackageParser {
    private final ChecksumGenerator checksumGenerator;

    public AuthenticationPackageParserImpl(ChecksumGenerator checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    @Override
    public String parsePackage(GenericPackage genericPackage) {
        AuthenticationPackage authenticationPackage = (AuthenticationPackage) genericPackage;
        String connectionType = authenticationPackage.getConnectionType().getAuthIdentifier();
        String account = authenticationPackage.getAccount();
        String macAddress = authenticationPackage.getMacAddress();

        String hexString = "0794" + connectionType + account + macAddress;
        String checksum = checksumGenerator.generateChecksum(hexString);
        return formatBytes(hexString + checksum).toUpperCase();
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        if (!(genericPackage instanceof AuthenticationPackage)) {
            return false;
        }

        return genericPackage.getPackageType() == PackageType.AUTHENTICATION;
    }

    private String formatBytes(String hexString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            result.append(hexString, i, i + 2);
            result.append(" ");
        }

        return result.toString().trim();
    }
}
