package br.com.fulltime.fullarm.app.javafx.controller.panel;

import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.app.javafx.controller.Controller;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.packet.sender.EventSender;
import br.com.fulltime.fullarm.core.panel.Panel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
    private Button setOffButton;
    @FXML
    private Button cancelSetOffButton;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private EventPackageGenerator eventPackageGenerator;
    private String eventCode;

    public void armDisarmPanel() {
        if (!Panel.armed) {
            Logger.log("Armando painel");
            armPanelButton.setText("Desarmar");
            setOffButton.setDisable(false);
            panelStatusLabel.setText("Armado");
            panelStatusLabel.setTextFill(Colors.RED);

            Panel.armed = true;
            eventCode = "3401";
            EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
            eventSender.sendEvent(eventPackage);
            return;
        }

        Logger.log("Desarmando painel");
        armPanelButton.setText("Armar");
        setOffButton.setDisable(true);
        cancelSetOffButton.setDisable(true);
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(Colors.GREEN);

        Panel.armed = false;
        eventCode = "1401";
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
        eventSender.sendEvent(eventPackage);
    }

    public void setOff() {
        Logger.log("Enviando evento de disparo");
        setOffButton.setDisable(true);
        cancelSetOffButton.setDisable(false);
        panelStatusLabel.setText("Disparado");

        eventCode = "1130";
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
        eventSender.sendEvent(eventPackage);
    }

    public void restore() {
        Logger.log("Enviando evento de restauração");
        setOffButton.setDisable(false);
        cancelSetOffButton.setDisable(true);

        eventCode = "3130";
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
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
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(Colors.GREEN);
    }

    private void disableAllButtons() {
        armPanelButton.setDisable(true);
        setOffButton.setDisable(true);
        cancelSetOffButton.setDisable(true);
    }

    @FXML
    private void initialize() {
        updatePanelStatus(Panel.connected);
    }

    @Override
    public Pane getRoot() {
        return panelTab;
    }
}
