package br.com.fulltime.fullarm.core.packet.generator.event;

import br.com.fulltime.fullarm.core.packet.EventPackage;
import org.springframework.stereotype.Service;

@Service
public class EventPackageGeneratorImpl implements EventPackageGenerator {
    @Override
    public EventPackage generateEvent(String connectionType, String account, String eventCode, String partition) {
        EventPackage eventPackage = new EventPackage();
        eventPackage.setConnectionType(connectionType);
        eventPackage.setAccount(account);
        eventPackage.setContactId("18");
        eventPackage.setQualifier(eventCode.substring(0, 1));
        eventPackage.setEventCode(eventCode.substring(1));
        eventPackage.setPartition(partition);
        eventPackage.setArgument("001");

        return eventPackage;
    }
}
