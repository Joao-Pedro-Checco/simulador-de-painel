package br.com.fulltime.fullarm.core.packet.sender;

import br.com.fulltime.fullarm.core.packet.EventPackage;

public interface EventSender {
    void sendEvent(EventPackage eventPackage);
}
