package br.com.fulltime.fullarm.app.javafx.controller;

import br.com.fulltime.fullarm.infra.connection.handler.ConnectionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PanelController {
    @FXML
    private Label connectionStatusLabel;
    @FXML
    private TextField hostTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Label panelStatusLabel;
    @FXML
    private Button armPanelButton;
    @FXML
    private Button disarmPanelButton;
    @Autowired
    private ConnectionHandler connectionHandler;

    public void connectPanel() {
        String host = hostTextField.getText();
        String port = portTextField.getText();
        if (host.isEmpty() && port.isEmpty()) {
            showAlert();
            return;
        }

        int intPort = Integer.parseInt(port);
        connectionHandler.initializeConnection(host, intPort);

        connectionStatusLabel.setText("Conectado");
        connectionStatusLabel.setTextFill(Color.color(0, 1, 0));
        hostTextField.setDisable(true);
        portTextField.setDisable(true);
        connectButton.setDisable(true);
        disconnectButton.setDisable(false);
    }

    public void disconnectPanel() {
        connectionHandler.terminateConnection();

        connectionStatusLabel.setText("Desconectado");
        connectionStatusLabel.setTextFill(Color.color(1, 0, 0));
        hostTextField.setDisable(false);
        portTextField.setDisable(false);
        connectButton.setDisable(false);
        disconnectButton.setDisable(true);
    }

    public void armPanel() {
        panelStatusLabel.setTextFill(Color.color(1, 0, 0));
        panelStatusLabel.setText("Armado");
        armPanelButton.setDisable(true);
        disarmPanelButton.setDisable(false);
    }

    public void disarmPanel() {
        panelStatusLabel.setTextFill(Color.color(0, 1, 0));
        panelStatusLabel.setText("Desarmado");
        armPanelButton.setDisable(false);
        disarmPanelButton.setDisable(true);
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/fullarm-logo.png"));
        alert.setTitle("Erro!");
        alert.setHeaderText("Os dados informados não são válidos! Por favor, tente novamente!");
        alert.showAndWait();
    }
}
