package br.com.fulltime.fullarm.core.packet.nack;

public enum NackType {
    INVALID_PACKAGE_FORMAT("E0"),
    WRONG_PASSWORD("E1"),
    INVALID_COMMAND("E2"),
    PANEL_NOT_PARTITIONED("E3"),
    OPEN_ZONES("E4"),
    DISCONTINUED_COMMAND("E5"),
    USER_NOT_ALLOWED_TO_BYPASS("E6"),
    USER_NOT_ALLOWED_TO_DISARM("E7"),
    BYPASS_NOT_ALLOWED_WHEN_PANEL_ARMED("E8");

    private final String identifier;

    NackType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
