package br.com.fulltime.fullarm.core.connection.authenticator;

import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;

public interface PanelAuthenticator {
    boolean authenticatePanel(AuthenticationPackage authenticationPackage);
}
