package br.com.fulltime.fullarm.app.javafx.generator.list.sector.config;

import br.com.fulltime.fullarm.app.javafx.controller.config.ConfigurationPaneController;
import br.com.fulltime.fullarm.app.javafx.generator.hbox.sector.config.SectorConfigHBoxGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class SectorConfigListGeneratorImpl implements SectorConfigListGenerator {
    private final SectorConfigHBoxGenerator sectorConfigHBoxGenerator;
    private ConfigurationPaneController controller;

    public SectorConfigListGeneratorImpl(SectorConfigHBoxGenerator sectorConfigHBoxGenerator) {
        this.sectorConfigHBoxGenerator = sectorConfigHBoxGenerator;
    }

    @Override
    public void generateList(ListView<HBox> listView) {
        if (!listView.getItems().isEmpty()) {
            listView.getItems().clear();
        }

        sectorConfigHBoxGenerator.setConfigController(controller);
        Panel.getZones().forEach(zone -> {
            HBox hBox = sectorConfigHBoxGenerator.generateHBox(zone);
            listView.getItems().add(hBox);
        });
    }

    @Override
    public void setController(ConfigurationPaneController controller) {
        this.controller = controller;
    }
}
