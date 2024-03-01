package br.com.fulltime.fullarm.core.packet;

public class AuthenticationPackage extends GenericPackage {
    private String connectionType;
    private String account;
    private String macAddress;

    public AuthenticationPackage() {
        super(PackageType.AUTHENTICATION);
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
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
