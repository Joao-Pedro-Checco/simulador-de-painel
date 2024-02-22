package br.com.fulltime.fullarm.core.packet.generator.authentication;

public interface AuthenticationPackageGenerator {
    String generatePackage(String connectionType, String account, String macAddress);
}
