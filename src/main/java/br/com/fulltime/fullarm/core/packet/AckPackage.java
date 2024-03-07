package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.core.packet.constants.PackageType;

public class AckPackage extends GenericPackage {
    public AckPackage() {
        super(PackageType.ACK);
    }
}
