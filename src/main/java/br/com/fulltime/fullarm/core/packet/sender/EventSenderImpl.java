package br.com.fulltime.fullarm.core.packet.sender;

import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

@Service
public class EventSenderImpl implements EventSender {
    private final PackageSender packageSender;

    public EventSenderImpl(PackageSender packageSender) {
        this.packageSender = packageSender;
    }

    @Override
    public void sendEvent(String eventPackage) {
        packageSender.sendPackage(eventPackage);
    }
}
