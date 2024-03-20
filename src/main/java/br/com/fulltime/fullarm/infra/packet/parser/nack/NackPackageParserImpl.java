package br.com.fulltime.fullarm.infra.packet.parser.nack;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.core.packet.nack.NackPackage;
import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import org.springframework.stereotype.Service;

@Service
public class NackPackageParserImpl implements NackPackageParser {
    private final ChecksumGenerator checksumGenerator;

    public NackPackageParserImpl(ChecksumGenerator checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    @Override
    public String parsePackage(GenericPackage genericPackage) {
        NackPackage nackPackage = (NackPackage) genericPackage;
        String startOfCommand = "02 E9";
        String nackIdentifier = nackPackage.getNackType().getIdentifier();
        String checksum = checksumGenerator.generateChecksum(startOfCommand + nackIdentifier);

        return String.format("%s %s %s", startOfCommand, nackIdentifier, checksum);
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        if (!(genericPackage instanceof NackPackage)) {
            return false;
        }

        return genericPackage.getPackageType() == PackageType.NACK;
    }
}
