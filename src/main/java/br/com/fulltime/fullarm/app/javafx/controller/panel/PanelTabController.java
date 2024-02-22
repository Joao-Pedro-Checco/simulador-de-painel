package br.com.fulltime.fullarm.app.javafx.controller.panel;

import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.app.javafx.controller.Controller;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.packet.sender.EventSender;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PanelTabController implements Controller {
    @FXML
    private Pane panelTab;
    @FXML
    private Label panelStatusLabel;
    @FXML
    private Button armPanelButton;
    @FXML
    private Button disarmPanelButton;
    @FXML
    private Button stayArmPanelButton;
    @FXML
    private RadioButton ethernetIp1Radio;
    @FXML
    private RadioButton ethernetIp2Radio;
    @FXML
    private RadioButton gprsIp1Radio;
    @FXML
    private RadioButton gprsIp2Radio;
    @FXML
    private Button setOffButton;
    @FXML
    private Button cancelSetOffButton;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private EventPackageGenerator eventPackageGenerator;
    private String connectionType;
    private String account;
    private String eventCode;
    private final String partition = "01";

    public void armPanel() {
        Logger.log("Armando painel");
        armPanelButton.setDisable(true);
        stayArmPanelButton.setDisable(true);
        disarmPanelButton.setDisable(false);
        setOffButton.setDisable(false);
        panelStatusLabel.setText("Armado");
        panelStatusLabel.setTextFill(Colors.RED);

        setRadioStatus(true);

        connectionType = getConnectionType();
        eventCode = "3401";

        String eventPackage = eventPackageGenerator.generateEvent(connectionType, account, eventCode, partition);
        eventSender.sendEvent(eventPackage);
    }

    public void stayArmPanel() {
        Logger.log("Armando painel (Stay)");
        stayArmPanelButton.setDisable(true);
        armPanelButton.setDisable(true);
        disarmPanelButton.setDisable(false);
        setOffButton.setDisable(false);
        panelStatusLabel.setText("Armado Stay");
        panelStatusLabel.setTextFill(Color.color(0.9, 0.6, 0));

        setRadioStatus(true);

        connectionType = getConnectionType();
        eventCode = "3441";

        String eventPackage = eventPackageGenerator.generateEvent(connectionType, account, eventCode, partition);
        eventSender.sendEvent(eventPackage);
    }

    public void disarmPanel() {
        Logger.log("Desarmando painel");
        armPanelButton.setDisable(false);
        stayArmPanelButton.setDisable(false);
        disarmPanelButton.setDisable(true);
        setOffButton.setDisable(true);
        cancelSetOffButton.setDisable(true);
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(Colors.GREEN);

        setRadioStatus(false);

        connectionType = getConnectionType();
        eventCode = "1401";

        String eventPackage = eventPackageGenerator.generateEvent(connectionType, account, eventCode, partition);
        eventSender.sendEvent(eventPackage);
    }

    public void setOff() {
        Logger.log("Enviando evento de disparo");
        setOffButton.setDisable(true);
        cancelSetOffButton.setDisable(false);
        panelStatusLabel.setText("Disparado");

        connectionType = getConnectionType();
        eventCode = "1130";

        String eventPackage = eventPackageGenerator.generateEvent(connectionType, account, eventCode, partition);
        eventSender.sendEvent(eventPackage);
    }

    public void cancelSetOff() {
        Logger.log("Enviando evento de restauração");
        setOffButton.setDisable(false);
        cancelSetOffButton.setDisable(true);

        connectionType = getConnectionType();
        eventCode = "3130";

        String eventPackage = eventPackageGenerator.generateEvent(connectionType, account, eventCode, partition);
        eventSender.sendEvent(eventPackage);
    }

    public void updatePanelStatus(boolean connected) {
        if (!connected) {
            panelStatusLabel.setText("Desconectado");
            panelStatusLabel.setTextFill(Colors.GREY);
            disableAllButtons();
            return;
        }

        armPanelButton.setDisable(false);
        stayArmPanelButton.setDisable(false);
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(Colors.GREEN);
    }

    private void disableAllButtons() {
        armPanelButton.setDisable(true);
        stayArmPanelButton.setDisable(true);
        disarmPanelButton.setDisable(true);
        setOffButton.setDisable(true);
        cancelSetOffButton.setDisable(true);
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String getConnectionType() {
        if (ethernetIp1Radio.isSelected()) {
            return "11";
        }

        if (ethernetIp2Radio.isSelected()) {
            return "12";
        }

        if (gprsIp1Radio.isSelected()) {
            return "21";
        }

        if (gprsIp2Radio.isSelected()) {
            return "22";
        }

        return "00";
    }

    private void setRadioStatus(boolean isDisabled) {
        ethernetIp1Radio.setDisable(isDisabled);
        ethernetIp2Radio.setDisable(isDisabled);
        gprsIp1Radio.setDisable(isDisabled);
        gprsIp2Radio.setDisable(isDisabled);
    }

    @Override
    public Pane getRoot() {
        return panelTab;
    }
}
