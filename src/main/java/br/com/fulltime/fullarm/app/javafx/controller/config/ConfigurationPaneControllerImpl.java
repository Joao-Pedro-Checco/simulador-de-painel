package br.com.fulltime.fullarm.app.javafx.controller.config;

import br.com.fulltime.fullarm.app.javafx.generator.list.sector.config.SectorConfigListGenerator;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigurationPaneControllerImpl implements ConfigurationPaneController {
    @FXML
    private Pane configPane;
    @FXML
    private Spinner<Integer> keepAliveTimeSpinner;
    @FXML
    private ToggleSwitch openSectorsArmSwitch;
    @FXML
    private Button saveConfigurationsButton;
    @FXML
    private Button enableAllButton;
    @FXML
    private Button disableAllButton;
    @FXML
    private ListView<HBox> sectorListView;
    @Autowired
    private SectorConfigListGenerator sectorConfigListGenerator;
    private Integer currentSpinnerValue;

    public void saveConfigurations() {
        Logger.log("Salvando configurações");
        Panel.setCanArmWithOpenZones(openSectorsArmSwitch.isSelected());
        Panel.setKeepAliveTime(currentSpinnerValue);
        saveSectorConfigurations();
    }

    public void enableAllSectors() {
        sectorListView.getItems().forEach(hBox -> {
            ToggleSwitch toggleSwitch = (ToggleSwitch) hBox.getChildren().get(1);
            toggleSwitch.setSelected(true);
        });

        updateSectorButtons();
    }

    public void disableAllSectors() {
        sectorListView.getItems().forEach(hBox -> {
            ToggleSwitch toggleSwitch = (ToggleSwitch) hBox.getChildren().get(1);
            toggleSwitch.setSelected(false);
        });

        updateSectorButtons();
    }

    private void saveSectorConfigurations() {
        List<Zone> zones = Panel.getZones();
        for (int i = 0; i < zones.size(); i++) {
            Zone zone = zones.get(i);

            HBox hBox = sectorListView.getItems().get(i);
            ToggleSwitch toggleSwitch = (ToggleSwitch) hBox.getChildren().get(1);
            ChoiceBox<Integer> choiceBox = (ChoiceBox<Integer>) hBox.getChildren().get(2);

            zone.setEnabled(toggleSwitch.isSelected());
            zone.setPartition(Panel.getPartitions().get(choiceBox.getValue() - 1));
        }
    }

    @FXML
    private void initialize() {
        sectorConfigListGenerator.setController(this);
        initializeSpinner();
        initializeListView();
        openSectorsArmSwitch.setSelected(Panel.isCanArmWithOpenZones());
        updateSectorButtons();
    }

    private void updateSectorButtons() {
        boolean allSectorsEnabled = sectorListView.getItems().stream().allMatch(hBox -> {
            ToggleSwitch toggleSwitch = (ToggleSwitch) hBox.getChildren().get(1);
            return toggleSwitch.isSelected();
        });

        boolean allSectorsDisabled = sectorListView.getItems().stream().allMatch(hBox -> {
            ToggleSwitch toggleSwitch = (ToggleSwitch) hBox.getChildren().get(1);
            return !toggleSwitch.isSelected();
        });

        enableAllButton.setDisable(allSectorsEnabled);
        disableAllButton.setDisable(allSectorsDisabled);
    }

    private void initializeListView() {
        sectorConfigListGenerator.generateList(sectorListView);
    }

    private void initializeSpinner() {
        currentSpinnerValue = 1;

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        spinnerValueFactory.setValue(currentSpinnerValue);
        keepAliveTimeSpinner.setValueFactory(spinnerValueFactory);

        keepAliveTimeSpinner.setOnMouseClicked(event -> currentSpinnerValue = keepAliveTimeSpinner.getValue());
    }

    @Override
    public void onLoad() {
        initialize();
    }

    @Override
    public Pane getRoot() {
        return configPane;
    }

    @Override
    public void onToggle() {
        updateSectorButtons();
    }
}
