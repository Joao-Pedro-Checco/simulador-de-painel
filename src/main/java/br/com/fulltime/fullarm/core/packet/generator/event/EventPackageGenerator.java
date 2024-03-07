package br.com.fulltime.fullarm.core.packet.generator.event;

import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.panel.ConnectionType;

public interface EventPackageGenerator {
    EventPackage generateEvent(EventCode eventCode);
}
