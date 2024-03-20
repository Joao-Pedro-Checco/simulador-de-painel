package br.com.fulltime.fullarm.core.packet.nack;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;

public class NackPackage extends GenericPackage {
    private final NackType nackType;

    public NackPackage(NackType nackType) {
        super(PackageType.NACK);
        this.nackType = nackType;
    }

    public NackType getNackType() {
        return nackType;
    }
}
