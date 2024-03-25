package br.com.fulltime.fullarm.app.javafx.generator.list.sector.config;

import br.com.fulltime.fullarm.app.javafx.controller.config.ConfigurationPaneController;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public interface SectorConfigListGenerator {
    void generateList(ListView<HBox> listView);
    void setController(ConfigurationPaneController controller);
}
