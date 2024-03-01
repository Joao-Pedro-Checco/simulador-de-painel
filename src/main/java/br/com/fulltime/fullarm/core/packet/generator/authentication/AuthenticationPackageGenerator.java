package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;

public interface AuthenticationPackageGenerator {
    AuthenticationPackage generatePackage(String connectionType, String account, String macAddress);
}
