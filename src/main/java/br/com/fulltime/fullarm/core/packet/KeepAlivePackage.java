package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.core.packet.constants.PackageType;

public class KeepAlivePackage extends GenericPackage {
    public KeepAlivePackage() {
        super(PackageType.KEEP_ALIVE);
    }
}
