package br.com.fulltime.fullarm.infra.packet.parser;

import br.com.fulltime.fullarm.core.packet.GenericPackage;

public interface PackageParserFactory {
    PackageParser getParser(GenericPackage genericPackage);
}
