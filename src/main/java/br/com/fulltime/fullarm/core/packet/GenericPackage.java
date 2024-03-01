package br.com.fulltime.fullarm.core.packet;

public abstract class GenericPackage {
    private final PackageType packageType;

    public GenericPackage(PackageType packageType) {
        this.packageType = packageType;
    }

    public PackageType getPackageType() {
        return packageType;
    }
}
