package br.com.fulltime.fullarm.core.packet.keepalive;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;

public class KeepAlivePackage extends GenericPackage {
    public KeepAlivePackage() {
        super(PackageType.KEEP_ALIVE);
    }
}
