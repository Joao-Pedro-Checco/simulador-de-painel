package br.com.fulltime.fullarm.app.javafx.controller.panel;

import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import br.com.fulltime.fullarm.core.panel.handler.PanelHandler;
import br.com.fulltime.fullarm.core.panel.listener.PanelStatusListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PanelTabControllerImpl implements PanelTabController {
    @FXML
    private Pane panelTab;
    @FXML
    private Label panelStatusLabel;
    @FXML
    private Button armPanelButton;
    @FXML
    private CheckBox partitionToggle;
    @FXML
    private ListView<HBox> sectorListView;
    @FXML
    private Label partitionLabel;
    @FXML
    private Button previousPartitionButton;
    @FXML
    private Button nextPartitionButton;
    @FXML
    private CheckBox openZonesArmToggle;
    @FXML
    private Button armPartitionButton;
    @Autowired
    private PanelStatusListener panelStatusListener;
    @Autowired
    private PanelHandler panelHandler;
    private int currentPartition = 1;

    public void armDisarmPanel() {
        if (!Panel.isArmed()) {
            panelHandler.armPanel();
            updateArmStatus(Panel.isArmed());
            return;
        }

        panelHandler.disarmPanel();
        updateArmStatus(Panel.isArmed());
    }

    public void partitionPanel() {
        if (partitionToggle.isSelected()) {
            panelHandler.partitionPanel();
            armPartitionButton.setDisable(false);
            armPanelButton.setDisable(true);
            panelStatusLabel.setText("Particionado");
            panelStatusLabel.setTextFill(Colors.BLACK);
            return;
        }

        panelHandler.unPartitionPanel();
        armPartitionButton.setDisable(true);
        armPanelButton.setDisable(false);
        updateArmStatus(Panel.isArmed());
    }

    public void goToPreviousPartition() {
        if (currentPartition > 1) {
            currentPartition--;
        }

        updateSectorList(currentPartition);
    }

    public void goToNextPartition() {
        if (currentPartition < 4) {
            currentPartition++;
        }

        updateSectorList(currentPartition);
    }

    public void allowArmWithOpenZones() {
        panelHandler.setCanArmWithOpenZones(openZonesArmToggle.isSelected());
    }

    public void armPartition() {
        Partition partition = Panel.getPartitions().get(currentPartition - 1);
        if (!partition.isActivated()) {
            panelHandler.armPartition(partition);
            updatePartitionStatus(partition.isActivated());
            return;
        }

        panelHandler.disarmPartition(partition);
        updatePartitionStatus(partition.isActivated());
    }

    private void updatePartitionStatus(boolean isArmed) {
        if (isArmed) {
            armPartitionButton.setText("Desarmar");
            partitionToggle.setDisable(true);
            return;
        }

        armPartitionButton.setText("Armar");
        partitionToggle.setDisable(false);
    }

    public void updatePanelConnection(boolean connected) {
        if (!connected) {
            panelStatusLabel.setText("Desconectado");
            panelStatusLabel.setTextFill(Colors.GREY);
            changeFieldsDisableStatus(true);
            return;
        }

        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(Colors.GREEN);
        changeFieldsDisableStatus(false);
    }

    private void changeFieldsDisableStatus(boolean disabled) {
        armPanelButton.setDisable(disabled);
        partitionToggle.setDisable(disabled);
        sectorListView.setDisable(disabled);
        previousPartitionButton.setDisable(disabled);
        nextPartitionButton.setDisable(disabled);
        openZonesArmToggle.setDisable(disabled);
    }

    @Override
    public void updateArmStatus(boolean armed) {
        if (!armed) {
            panelStatusLabel.setText("Desarmado");
            panelStatusLabel.setTextFill(Colors.GREEN);
            armPanelButton.setText("Armar");
            partitionToggle.setDisable(false);
            return;
        }

        panelStatusLabel.setText("Armado");
        panelStatusLabel.setTextFill(Colors.RED);
        armPanelButton.setText("Desarmar");
        partitionToggle.setDisable(true);
    }

    @FXML
    private void initialize() {
        panelStatusListener.setController(this);
        updatePanelConnection(Panel.isConnected());
        if (Panel.isConnected()) {
            updateArmStatus(Panel.isArmed());
        }
        updateSectorList(currentPartition);
    }

    @Override
    public Pane getRoot() {
        return panelTab;
    }

    private void updateSectorList(int currentPartition) {
        if (!sectorListView.getItems().isEmpty()) {
            sectorListView.getItems().clear();
        }

        partitionLabel.setText("Partição " + currentPartition);
        List<Zone> zones = Panel.getPartitions().get(currentPartition - 1).getZones();
        zones.forEach(zone -> {
            HBox hBox = new HBox();
            Button openCloseButton = new Button(zone.isOpen() ? "Fechar" : "Abrir");

            int zoneNumber = zone.getZoneNumber();
            String text = "Setor " + (zoneNumber <= 9 ? "0" + zoneNumber : zoneNumber);
            openCloseButton.setOnAction(event -> openCloseSector(zone, openCloseButton));
            hBox.getChildren().addAll(new Text(text), openCloseButton);
            hBox.setSpacing(220);

            sectorListView.getItems().add(hBox);
        });
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
