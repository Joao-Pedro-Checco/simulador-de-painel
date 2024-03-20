package br.com.fulltime.fullarm.core.panel.handler.zone;

import br.com.fulltime.fullarm.core.panel.components.Zone;

public interface ZoneHandler {
    void toggleZoneOpenState(Zone zone);
    void toggleZoneBypassState(Zone zone);
    void toggleZoneTamperState(Zone zone);
    void toggleShortCircuitState(Zone zone);
    void toggleLowBatteryState(Zone zone);
}
