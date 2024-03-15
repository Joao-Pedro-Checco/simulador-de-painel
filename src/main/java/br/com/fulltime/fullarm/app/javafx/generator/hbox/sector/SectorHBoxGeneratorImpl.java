package br.com.fulltime.fullarm.app.javafx.generator.hbox.sector;

import br.com.fulltime.fullarm.core.panel.components.Zone;
import br.com.fulltime.fullarm.core.panel.handler.PanelHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class SectorHBoxGeneratorImpl implements SectorHBoxGenerator {
    private final PanelHandler panelHandler;

    public SectorHBoxGeneratorImpl(PanelHandler panelHandler) {
        this.panelHandler = panelHandler;
    }

    @Override
    public HBox generateSector(Zone zone) {
        HBox hBox = new HBox();
        Button openCloseButton = new Button(zone.isOpen() ? "Fechar" : "Abrir");

        int zoneNumber = zone.getZoneNumber();
        String text = "Setor " + (zoneNumber <= 9 ? "0" + zoneNumber : zoneNumber);
        openCloseButton.setOnAction(event -> openCloseSector(zone, openCloseButton));
        hBox.getChildren().addAll(new Text(text), openCloseButton);
        hBox.setSpacing(220);

        return hBox;
    }

    private void openCloseSector(Zone zone, Button openCloseButton) {
        if (!zone.isOpen()) {
            panelHandler.openZone(zone);
            openCloseButton.setText("Fechar");
            return;
        }

        panelHandler.closeZone(zone);
        openCloseButton.setText("Abrir");
    }
}
