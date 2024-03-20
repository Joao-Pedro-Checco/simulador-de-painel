package br.com.fulltime.fullarm.app.javafx.generator.hbox.sector;

import br.com.fulltime.fullarm.core.panel.components.Zone;
import br.com.fulltime.fullarm.core.panel.handler.zone.ZoneHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class SectorHBoxGeneratorImpl implements SectorHBoxGenerator {
    private final ZoneHandler zoneHandler;

    public SectorHBoxGeneratorImpl(ZoneHandler zoneHandler) {
        this.zoneHandler = zoneHandler;
    }

    @Override
    public HBox generateSector(Zone zone) {
        HBox hBox = new HBox();

        int zoneNumber = zone.getZoneNumber();
        String text = "" + (zoneNumber <= 9 ? "0" + zoneNumber : zoneNumber);
        CheckBox openCheckBox = new CheckBox();
        CheckBox bypassCheckBox = new CheckBox();
        CheckBox tamperCheckBox = new CheckBox();
        CheckBox shortCircuitCheckBox = new CheckBox();
        CheckBox lowBatteryCheckBox = new CheckBox();

        openCheckBox.setOnAction(event -> zoneHandler.toggleZoneOpenState(zone));
        bypassCheckBox.setOnAction(event -> zoneHandler.toggleZoneBypassState(zone));
        tamperCheckBox.setOnAction(event -> zoneHandler.toggleZoneTamperState(zone));
        shortCircuitCheckBox.setOnAction(event -> zoneHandler.toggleShortCircuitState(zone));
        lowBatteryCheckBox.setOnAction(event -> zoneHandler.toggleLowBatteryState(zone));
        hBox.getChildren().addAll(new Text(text), openCheckBox, bypassCheckBox, tamperCheckBox,
                shortCircuitCheckBox, lowBatteryCheckBox);
        hBox.setSpacing(55);

        return hBox;
    }
}
