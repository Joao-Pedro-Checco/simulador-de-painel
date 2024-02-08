package br.com.fulltime.fullarm.core.packet;

public interface EventPackageGenerator {
    String generateEvent(String connectionType, String account, String eventCode, String partition);
}
