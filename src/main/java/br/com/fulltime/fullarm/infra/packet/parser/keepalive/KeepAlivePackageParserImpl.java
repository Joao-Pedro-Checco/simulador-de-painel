package br.com.fulltime.fullarm.infra.packet.parser.keepalive;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.core.packet.keepalive.KeepAlivePackage;
import org.springframework.stereotype.Service;

@Service
public class KeepAlivePackageParserImpl implements KeepAlivePackageParser {
    @Override
    public String parsePackage(GenericPackage genericPackage) {
        return "F7";
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        if (!(genericPackage instanceof KeepAlivePackage)) {
            return false;
        }

        return genericPackage.getPackageType() == PackageType.KEEP_ALIVE;
    }
}
