package br.com.fulltime.fullarm.infra.packet.parser.ack;

import br.com.fulltime.fullarm.core.packet.AckPackage;
import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.PackageType;
import org.springframework.stereotype.Service;

@Service
public class AckPackageParserImpl implements AckPackageParser {
    @Override
    public String parsePackage(GenericPackage genericPackage) {
        return "02 E9 FE EA";
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        if (!(genericPackage instanceof AckPackage)) {
            return false;
        }

        return genericPackage.getPackageType() == PackageType.ACK;
    }
}
