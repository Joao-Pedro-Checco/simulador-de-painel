package br.com.fulltime.fullarm.core.packet.generator.event;

public interface EventPackageGenerator {
    String generateEvent(String connectionType, String account, String eventCode, String partition);
}
