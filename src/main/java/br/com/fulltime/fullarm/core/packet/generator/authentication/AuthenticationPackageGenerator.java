package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;

public interface AuthenticationPackageGenerator {
    AuthenticationPackage generatePackage();
}
