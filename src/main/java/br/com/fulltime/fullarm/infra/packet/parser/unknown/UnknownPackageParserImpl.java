package br.com.fulltime.fullarm.infra.packet.parser.unknown;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.GenericPackage;
import org.springframework.stereotype.Service;

@Service
public class UnknownPackageParserImpl implements UnknownPackageParser {
    @Override
    public String parsePackage(GenericPackage genericPackage) {
        Logger.log("O pacote recebido pelo parser não foi reconhecido");
        return null;
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        return true;
    }
}
