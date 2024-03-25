package br.com.fulltime.fullarm.app.javafx.generator.hbox.sector.config;

import br.com.fulltime.fullarm.app.javafx.controller.config.ConfigurationPaneController;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import javafx.scene.layout.HBox;

public interface SectorConfigHBoxGenerator {
    HBox generateHBox(Zone zone);
    void setConfigController(ConfigurationPaneController controller);
}
