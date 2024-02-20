package br.com.fulltime.fullarm.infra.packet.processor;

public interface ProcessorFactory {
    PackageProcessor getProcessor(String hexString);
}
