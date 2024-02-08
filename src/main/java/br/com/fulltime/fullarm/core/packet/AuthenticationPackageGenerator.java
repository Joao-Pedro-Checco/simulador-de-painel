package br.com.fulltime.fullarm.core.packet;

public interface AuthenticationPackageGenerator {
    String generatePackage(String connectionType, String account, String macAddress);
}
