package br.com.fulltime.fullarm.core.packet.ack;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;

public class AckPackage extends GenericPackage {
    public AckPackage() {
        super(PackageType.ACK);
    }
}
