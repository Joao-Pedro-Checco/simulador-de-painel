package br.com.fulltime.fullarm.core.packet.generator.event;

import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import org.springframework.stereotype.Service;

@Service
public class EventPackageGeneratorImpl implements EventPackageGenerator {
    @Override
    public EventPackage generateEvent(EventCode eventCode, Partition partition, int argument) {
        EventPackage eventPackage = new EventPackage();
        eventPackage.setConnectionType(Panel.getConnectionType());
        eventPackage.setAccount(Panel.getAccount());
        eventPackage.setContactId("18");
        eventPackage.setQualifier(eventCode.getCode().substring(0, 1));
        eventPackage.setEventCode(eventCode);
        eventPackage.setPartition(partition);
        eventPackage.setArgument(argument);

        return eventPackage;
    }
}
