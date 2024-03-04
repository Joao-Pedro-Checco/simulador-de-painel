package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;
import br.com.fulltime.fullarm.core.panel.ConnectionType;

public interface AuthenticationPackageGenerator {
    AuthenticationPackage generatePackage(ConnectionType connectionType, String macAddress);
}
