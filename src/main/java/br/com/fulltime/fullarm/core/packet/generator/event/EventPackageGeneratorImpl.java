package br.com.fulltime.fullarm.core.packet.generator.event;

import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.panel.ConnectionType;
import br.com.fulltime.fullarm.core.panel.Panel;
import org.springframework.stereotype.Service;

@Service
public class EventPackageGeneratorImpl implements EventPackageGenerator {
    @Override
    public EventPackage generateEvent(String eventCode) {
        EventPackage eventPackage = new EventPackage();
        eventPackage.setConnectionType(Panel.connectionType);
        eventPackage.setAccount(Panel.account);
        eventPackage.setContactId("18");
        eventPackage.setQualifier(eventCode.substring(0, 1));
        eventPackage.setEventCode(eventCode.substring(1));
        eventPackage.setPartition("01");
        eventPackage.setArgument("001");

        return eventPackage;
    }
}
