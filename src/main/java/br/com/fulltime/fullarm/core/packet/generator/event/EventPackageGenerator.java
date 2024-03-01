package br.com.fulltime.fullarm.core.packet.generator.event;

import br.com.fulltime.fullarm.core.packet.EventPackage;

public interface EventPackageGenerator {
    EventPackage generateEvent(String connectionType, String account, String eventCode, String partition);
}
