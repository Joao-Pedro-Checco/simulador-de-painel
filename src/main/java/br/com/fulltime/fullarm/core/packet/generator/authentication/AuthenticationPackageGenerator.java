package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;
import br.com.fulltime.fullarm.core.panel.constants.ConnectionType;

public interface AuthenticationPackageGenerator {
    AuthenticationPackage generatePackage(ConnectionType connectionType, String macAddress);
}
