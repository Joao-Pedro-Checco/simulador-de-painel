package br.com.fulltime.fullarm.infra.packet.constants;

public enum PackageIdentifier {
    ACK("FE"),
    COMMAND("E9"),
    UNKNOWN("");

    private final String identifier;

    PackageIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static PackageIdentifier getByValue(String identifier) {
        for (PackageIdentifier pi : values()) {
            if (identifier.equals(pi.getIdentifier())) return pi;
        }

        return PackageIdentifier.UNKNOWN;
    }
}
