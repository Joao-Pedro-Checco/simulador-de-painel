package br.com.fulltime.fullarm.app.javafx.controller.panel;

import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.app.javafx.generator.list.pgm.PgmListGenerator;
import br.com.fulltime.fullarm.app.javafx.generator.list.sector.SectorListGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.handler.PanelHandler;
import br.com.fulltime.fullarm.core.panel.listener.PanelStatusListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PanelPaneControllerImpl implements PanelPaneController {
    @FXML
    private Pane panelPane;
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
    private ListView<HBox> pgmListView;
    @Autowired
    private SectorListGenerator sectorListGenerator;
    @Autowired
    private PanelStatusListener panelStatusListener;
    @Autowired
    private PanelHandler panelHandler;
    @Autowired
    private PgmListGenerator pgmListGenerator;
    private int currentPartition = 1;

    public void armDisarmPanel() {
        if (!Panel.isArmed()) {
            if (Panel.isPartitioned()) {
                armDisarmPartition();
                return;
            }

            panelHandler.armPanel();
            updateArmStatus(Panel.isArmed());
            return;
        }

        if (Panel.isPartitioned()) {
            armDisarmPartition();
            return;
        }

        panelHandler.disarmPanel();
        updateArmStatus(Panel.isArmed());
    }

    public void partitionPanel() {
        if (partitionToggle.isSelected()) {
            panelHandler.partitionPanel();
            setPartitionVisibility(true);
            updateSectorList(currentPartition);
            updateArmStatus(Panel.isArmed());
            return;
        }

        panelHandler.unPartitionPanel();
        setPartitionVisibility(false);
        currentPartition = 1;
        partitionLabel.setText("Partição 1");
        updateSectorList(currentPartition);
        updateArmStatus(Panel.isArmed());
    }

    private void setPartitionVisibility(boolean isVisible) {
        partitionLabel.setVisible(isVisible);
        previousPartitionButton.setVisible(isVisible);
        nextPartitionButton.setVisible(isVisible);
    }

    private void armDisarmPartition() {
        Partition partition = Panel.getPartitions().get(currentPartition - 1);
        if (!partition.isActivated()) {
            panelHandler.armPartition(partition);
            updatePartitionStatus(partition.isActivated());
            return;
        }

        panelHandler.disarmPartition(partition);
        updatePartitionStatus(partition.isActivated());
    }

    public void goToPreviousPartition() {
        if (currentPartition <= 1) {
            return;
        }

        currentPartition--;
        updateSectorList(currentPartition);
        Partition partition = Panel.getPartitions().get(currentPartition - 1);
        updatePartitionStatus(partition.isActivated());
    }

    public void goToNextPartition() {
        if (currentPartition >= 4) {
            return;
        }

        currentPartition++;
        updateSectorList(currentPartition);
        Partition partition = Panel.getPartitions().get(currentPartition - 1);
        updatePartitionStatus(partition.isActivated());
    }

    private void updatePartitionStatus(boolean isArmed) {
        partitionLabel.setText("Partição " + currentPartition);
        updateArmStatus(isArmed);
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
        pgmListView.setDisable(disabled);
        previousPartitionButton.setDisable(disabled);
        nextPartitionButton.setDisable(disabled);
    }

    public void updateArmStatus(boolean armed) {
        boolean anyPartitionIsActivated = Panel.getPartitions().stream().anyMatch(Partition::isActivated);
        partitionToggle.setDisable(anyPartitionIsActivated);

        Partition partition = Panel.getPartitions().get(currentPartition - 1);
        if (Panel.isPartitioned()) {
            armed = partition.isActivated();
        }

        if (!armed) {
            panelStatusLabel.setText("Desarmado");
            panelStatusLabel.setTextFill(Colors.GREEN);
            armPanelButton.setText("Armar");
            return;
        }

        panelStatusLabel.setText("Armado");
        panelStatusLabel.setTextFill(Colors.RED);
        armPanelButton.setText("Desarmar");
    }

    @FXML
    private void initialize() {
        panelStatusListener.setController(this);
        updatePanelConnection(Panel.isConnected());
        updateSectorList(currentPartition);
        updatePgmList();

        if (Panel.isConnected()) {
            updateArmStatus(Panel.isArmed());
        }
    }

    @Override
    public void onLoad() {
        initialize();
    }

    @Override
    public Pane getRoot() {
        return panelPane;
    }

    private void updateSectorList(int currentPartition) {
        Partition partition = Panel.getPartitions().get(currentPartition - 1);
        sectorListGenerator.generateList(sectorListView, partition);
    }

    private void updatePgmList() {
        pgmListGenerator.generateList(pgmListView);
    }

    @Override
    public void updateInterface() {
        updateArmStatus(Panel.isArmed());
        updateSectorList(currentPartition);
        updatePgmList();
    }
}
