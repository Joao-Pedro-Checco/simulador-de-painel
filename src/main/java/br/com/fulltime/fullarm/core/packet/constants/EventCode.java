package br.com.fulltime.fullarm.core.packet.constants;

public enum EventCode {
    ARM("3401"),
    DISARM("1401"),
    PGM_ACTIVATION("1422"),
    PGM_DEACTIVATION("3422"),
    BURGLARY_ALARM("1130"),
    ALARM_RESTORE("3130");

    private final String code;

    EventCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
