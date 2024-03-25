package br.com.fulltime.fullarm.app.javafx.generator.hbox.sector.config;

import br.com.fulltime.fullarm.app.javafx.controller.config.ConfigurationPaneController;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SectorConfigHBoxGeneratorImpl implements SectorConfigHBoxGenerator {
    private ConfigurationPaneController controller;

    @Override
    public HBox generateHBox(Zone zone) {
        HBox hBox = new HBox();

        int zoneNumber = zone.getZoneNumber();
        Text sectorText = new Text("" + (zoneNumber <= 9 ? "0" + zoneNumber : zoneNumber));
        ChoiceBox<Integer> partitionChoiceBox = getChoiceBox(zone);
        ToggleSwitch enabledSectorToggle = new ToggleSwitch();
        enabledSectorToggle.setSelected(zone.isEnabled());
        enabledSectorToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            controller.onToggle();
        });

        hBox.setSpacing(30);
        hBox.getChildren().addAll(sectorText, enabledSectorToggle, partitionChoiceBox);
        return hBox;
    }

    @Override
    public void setConfigController(ConfigurationPaneController controller) {
        this.controller = controller;
    }

    private ChoiceBox<Integer> getChoiceBox(Zone zone) {
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
        List<Integer> partitions = Panel.getPartitions().stream()
                .map(Partition::getPartitionNumber)
                .collect(Collectors.toList());

        choiceBox.getItems().addAll(partitions);
        Partition partition = zone.getPartition();
        choiceBox.getSelectionModel().select(partition.getPartitionNumber() - 1);
        return choiceBox;
    }
}
