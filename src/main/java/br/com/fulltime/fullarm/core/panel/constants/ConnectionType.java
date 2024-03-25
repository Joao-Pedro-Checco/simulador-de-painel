package br.com.fulltime.fullarm.core.panel.constants;

public enum ConnectionType {
    ETHERNET("45", "11"),
    GPRS("47", "21"),
    UNKNOWN("00", "00");

    private final String authIdentifier;
    private final String eventIdentifier;

    ConnectionType(String authIdentifier, String eventIdentifier) {
        this.authIdentifier = authIdentifier;
        this.eventIdentifier = eventIdentifier;
    }

    public String getAuthIdentifier() {
        return authIdentifier;
    }

    public String getEventIdentifier() {
        return eventIdentifier;
    }

    public static ConnectionType getByValue(String authIdentifier) {
        return ConnectionType.UNKNOWN;
    }
}
