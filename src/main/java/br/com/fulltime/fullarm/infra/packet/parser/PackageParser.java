package br.com.fulltime.fullarm.infra.packet.parser;

import br.com.fulltime.fullarm.core.packet.GenericPackage;

public interface PackageParser {
    String parsePackage(GenericPackage genericPackage);

    boolean canParse(GenericPackage genericPackage);
}
