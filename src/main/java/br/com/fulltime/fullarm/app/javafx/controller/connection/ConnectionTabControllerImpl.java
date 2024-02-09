package br.com.fulltime.fullarm.app.javafx.controller.connection;

import br.com.fulltime.fullarm.app.UserInputValidator;
import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelTabController;
import br.com.fulltime.fullarm.core.connection.initializer.ConnectionInitializer;
import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;
import br.com.fulltime.fullarm.core.connection.terminator.ConnectionTerminator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionTabControllerImpl implements ConnectionTabController {
    @FXML
    private Label connectionStatusLabel;
    @FXML
    private TextField hostTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private RadioButton ethernetRadioButton;
    @FXML
    private RadioButton gprsRadioButton;
    @FXML
    private TextField accountTextField;
    @FXML
    private TextField macAddressTextField;
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @Autowired
    private ConnectionInitializer connectionInitializer;
    @Autowired
    private ConnectionTerminator connectionTerminator;
    @Autowired
    private UserInputValidator userInputValidator;
    @Autowired
    private PanelTabController panelTabController;
    @Autowired
    private ConnectionListener connectionListener;

    public void connectPanel() {
        String host = hostTextField.getText();
        String port = portTextField.getText();
        String connectionType = setConnectionType();
        String account = accountTextField.getText();
        String macAddress = macAddressTextField.getText();

        if (!userInputValidator.isValid(host, port, account, macAddress)) {
            showAlert("Os dados informados não são válidos! Por favor, tente novamente!");
            return;
        }

        connectButton.setDisable(true);
        connectionStatusLabel.setText("Conectando...");
        connectionStatusLabel.setTextFill(Colors.YELLOW);

        int intPort = Integer.parseInt(port);
        connectionInitializer.initializeConnection(host, intPort, connectionType, account, macAddress);
    }

    public void disconnectPanel() {
        connectionTerminator.terminateConnection();
        panelTabController.updatePanelStatus(false);

        connectionStatusLabel.setText("Desconectado");
        connectionStatusLabel.setTextFill(Colors.GREY);
        setTextFieldStatus(false);
        connectButton.setDisable(false);
        disconnectButton.setDisable(true);
    }

    private void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/fullarm-logo.png"));
        alert.setTitle("Erro!");
        alert.setHeaderText(alertText);
        alert.showAndWait();
    }

    private String setConnectionType() {
        if (ethernetRadioButton.isSelected()) {
            return  "45";
        }

        if (gprsRadioButton.isSelected()) {
            return "47";
        }

        return "48";
    }

    @FXML
    public void initialize() {
        connectionListener.setConnectionController(this);
    }

    @Override
    public void updateConnectionStatus(boolean connected) {
        panelTabController.updatePanelStatus(connected);

        if (!connected) {
            connectionStatusLabel.setText("Desconectado");
            connectionStatusLabel.setTextFill(Colors.GREY);
            setTextFieldStatus(false);
            connectButton.setDisable(false);
            disconnectButton.setDisable(true);
            showAlert("Não foi possível se conectar com o servidor. Verifique as informações digitadas.");
            return;
        }

        panelTabController.setAccount(accountTextField.getText());
        connectionStatusLabel.setText("Conectado");
        connectionStatusLabel.setTextFill(Colors.GREEN);
        setTextFieldStatus(true);
        connectButton.setDisable(true);
        disconnectButton.setDisable(false);
    }

    private void setTextFieldStatus(boolean isDisabled) {
        hostTextField.setDisable(isDisabled);
        portTextField.setDisable(isDisabled);
        ethernetRadioButton.setDisable(isDisabled);
        gprsRadioButton.setDisable(isDisabled);
        accountTextField.setDisable(isDisabled);
        macAddressTextField.setDisable(isDisabled);
    }
}
