package br.com.fulltime.fullarm.app.javafx.controller.panel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

@Component
public class PanelTabController {
    private boolean panelIsConnected;
    @FXML
    private Label panelStatusLabel;
    @FXML
    private Button armPanelButton;
    @FXML
    private Button disarmPanelButton;
    private final Color green = Color.color(0, 0.7, 0);
    private final Color grey = Color.color(0.339, 0.339, 0.339);

    public void armPanel() {
        armPanelButton.setDisable(true);
        disarmPanelButton.setDisable(false);
        panelStatusLabel.setText("Armado");
        panelStatusLabel.setTextFill(Color.color(1, 0, 0));
    }

    public void disarmPanel() {
        armPanelButton.setDisable(false);
        disarmPanelButton.setDisable(true);
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(green);
    }

    public void updatePanelStatus(boolean connected) {
        panelIsConnected = connected;

        if (!connected) {
            panelStatusLabel.setText("Desconectado");
            panelStatusLabel.setTextFill(grey);
            armPanelButton.setDisable(true);
            disarmPanelButton.setDisable(true);
            return;
        }

        armPanelButton.setDisable(false);
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(green);
        armPanelButton.setDisable(false);
    }
}
