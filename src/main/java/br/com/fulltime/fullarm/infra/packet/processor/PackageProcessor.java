package br.com.fulltime.fullarm.infra.packet.processor;

public interface PackageProcessor {
    void processPackage(String hexString);

    boolean canProcess(String hexString);
}
