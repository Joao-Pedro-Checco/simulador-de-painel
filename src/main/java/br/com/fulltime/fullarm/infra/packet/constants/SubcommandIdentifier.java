package br.com.fulltime.fullarm.infra.packet.constants;

public enum SubcommandIdentifier {
    ARM("41"),
    BYPASS("42"),
    DISARM("44"),
    STATUS_REQUEST("5B"),
    UNKNOWN("");

    private final String identifier;

    SubcommandIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static SubcommandIdentifier getByValue(String identifier) {
        for (SubcommandIdentifier si : values()) {
            if (identifier.equals(si.getIdentifier())) return si;
        }

        return SubcommandIdentifier.UNKNOWN;
    }
}
