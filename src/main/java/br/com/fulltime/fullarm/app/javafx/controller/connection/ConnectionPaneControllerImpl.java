package br.com.fulltime.fullarm.app.javafx.controller.connection;

import br.com.fulltime.fullarm.app.UserInputValidator;
import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.app.javafx.Panes;
import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelPaneControllerImpl;
import br.com.fulltime.fullarm.core.connection.initializer.ConnectionInitializer;
import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;
import br.com.fulltime.fullarm.core.connection.terminator.ConnectionTerminator;
import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;
import br.com.fulltime.fullarm.core.packet.generator.authentication.AuthenticationPackageGenerator;
import br.com.fulltime.fullarm.core.panel.constants.ConnectionType;
import br.com.fulltime.fullarm.core.panel.Panel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.fulltime.fullarm.app.javafx.PaneMap.paneMap;

@Component
public class ConnectionPaneControllerImpl implements ConnectionPaneController {
    @FXML
    private Pane connectionPane;
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
    private TextField passwordTextField;
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
    private ConnectionListener connectionListener;
    @Autowired
    private AuthenticationPackageGenerator authenticationPackageGenerator;
    private PanelPaneControllerImpl panelTabController;

    public void connectPanel() {
        String host = hostTextField.getText();
        String port = portTextField.getText();
        ConnectionType connectionType = getConnectionType();
        Panel.setConnectionType(connectionType);
        String account = accountTextField.getText();
        String password = passwordTextField.getText();
        String macAddress = macAddressTextField.getText();

        if (!userInputValidator.isValid(host, port, account, password, macAddress)) {
            showAlert("Os dados informados não são válidos! Por favor, tente novamente!");
            return;
        }

        Panel.setAccount(account);
        Panel.setPassword(password);

        connectButton.setDisable(true);
        connectionStatusLabel.setText("Conectando...");
        connectionStatusLabel.setTextFill(Colors.YELLOW);

        int intPort = Integer.parseInt(port);
        AuthenticationPackage authenticationPackage =
                authenticationPackageGenerator.generatePackage(connectionType, macAddress);
        connectionInitializer.initializeConnection(host, intPort, authenticationPackage);
    }

    public void disconnectPanel() {
        connectionTerminator.terminateConnection();

        panelTabController = (PanelPaneControllerImpl) paneMap.get(Panes.PANEL);
        if (panelTabController != null) {
            panelTabController.updatePanelConnection(false);
        }

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

    private ConnectionType getConnectionType() {
        if (ethernetRadioButton.isSelected()) {
            return ConnectionType.ETHERNET;
        }

        if (gprsRadioButton.isSelected()) {
            return ConnectionType.GPRS;
        }

        return ConnectionType.UNKNOWN;
    }

    @FXML
    public void initialize() {
        connectionListener.setConnectionController(this);
    }

    @Override
    public void updateConnectionStatus(boolean connected) {
        if (!connected) {
            connectionStatusLabel.setText("Desconectado");
            connectionStatusLabel.setTextFill(Colors.GREY);
            setTextFieldStatus(false);
            connectButton.setDisable(false);
            disconnectButton.setDisable(true);
            showAlert("Não foi possível se conectar com o servidor. Verifique as informações digitadas.");
            return;
        }

        panelTabController = (PanelPaneControllerImpl) paneMap.get(Panes.PANEL);
        if (panelTabController != null) {
            panelTabController.updatePanelConnection(true);
        }

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
        passwordTextField.setDisable(isDisabled);
        macAddressTextField.setDisable(isDisabled);
    }


    @Override
    public void onLoad() {

    }

    @Override
    public Pane getRoot() {
        return connectionPane;
    }
}
