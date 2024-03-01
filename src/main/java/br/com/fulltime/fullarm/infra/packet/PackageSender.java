package br.com.fulltime.fullarm.infra.packet;

import br.com.fulltime.fullarm.core.packet.GenericPackage;

public interface PackageSender {
    void sendPackage(GenericPackage genericPackage);
}
