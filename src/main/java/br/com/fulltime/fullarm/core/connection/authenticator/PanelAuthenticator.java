package br.com.fulltime.fullarm.core.connection.authenticator;

import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;

public interface PanelAuthenticator {
    boolean authenticatePanel(AuthenticationPackage authenticationPackage);
}
