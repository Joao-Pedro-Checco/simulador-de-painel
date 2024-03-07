package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.core.packet.constants.PackageType;

public abstract class GenericPackage {
    private final PackageType packageType;

    public GenericPackage(PackageType packageType) {
        this.packageType = packageType;
    }

    public PackageType getPackageType() {
        return packageType;
    }
}
