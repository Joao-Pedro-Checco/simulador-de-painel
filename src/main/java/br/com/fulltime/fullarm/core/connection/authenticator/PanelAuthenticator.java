package br.com.fulltime.fullarm.core.connection.authenticator;

public interface PanelAuthenticator {
    void authenticatePanel(byte[] authenticationPackage);
}
