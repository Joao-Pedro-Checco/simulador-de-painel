package br.com.fulltime.fullarm.core.connection.authenticator;

public interface PanelAuthenticator {
    boolean authenticatePanel(byte[] authenticationPackage);
}
