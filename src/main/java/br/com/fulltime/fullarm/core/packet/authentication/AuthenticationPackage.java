package br.com.fulltime.fullarm.core.packet.authentication;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.core.panel.constants.ConnectionType;

public class AuthenticationPackage extends GenericPackage {
    private ConnectionType connectionType;
    private String account;
    private String macAddress;

    public AuthenticationPackage() {
        super(PackageType.AUTHENTICATION);
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
