package br.com.fulltime.fullarm.core.packet;

import br.com.fulltime.fullarm.core.packet.constants.EventCode;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.core.panel.ConnectionType;

public class EventPackage extends GenericPackage {
    private ConnectionType connectionType;
    private String account;
    private String contactId;
    private String qualifier;
    private EventCode eventCode;
    private String partition;
    private String argument;

    public EventPackage() {
        super(PackageType.EVENT);
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public EventCode getEventCode() {
        return eventCode;
    }

    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
