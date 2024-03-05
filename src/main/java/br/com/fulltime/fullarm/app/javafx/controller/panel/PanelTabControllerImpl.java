package br.com.fulltime.fullarm.app.javafx.controller.panel;

import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.EventPackage;
import br.com.fulltime.fullarm.core.packet.generator.event.EventPackageGenerator;
import br.com.fulltime.fullarm.core.packet.sender.EventSender;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.listener.PanelStatusListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PanelTabControllerImpl implements PanelTabController {
    @FXML
    private Pane panelTab;
    @FXML
    private Label panelStatusLabel;
    @FXML
    private Button armPanelButton;
    @FXML
    private Button setOffButton;
    @FXML
    private Button restoreButton;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private EventPackageGenerator eventPackageGenerator;
    @Autowired
    private PanelStatusListener panelStatusListener;
    private String eventCode;

    public void armDisarmPanel() {
        if (!Panel.isArmed()) {
            Panel.setArmed(true);
            Panel.getPartitions().forEach(p -> p.setActivated(true));
            eventCode = "3401";
            EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
            eventSender.sendEvent(eventPackage);

            Logger.log("Armando painel");
            armPanelButton.setText("Desarmar");
            setOffButton.setDisable(false);
            panelStatusLabel.setText("Armado");
            panelStatusLabel.setTextFill(Colors.RED);
            return;
        }

        Panel.setArmed(false);
        Panel.getPartitions().forEach(p -> p.setActivated(false));
        eventCode = "1401";
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
        eventSender.sendEvent(eventPackage);

        Logger.log("Desarmando painel");
        armPanelButton.setText("Armar");
        setOffButton.setDisable(true);
        restoreButton.setDisable(true);
        panelStatusLabel.setText("Desarmado");
        panelStatusLabel.setTextFill(Colors.GREEN);
    }

    public void setOff() {
        Logger.log("Enviando evento de disparo");
        setOffButton.setDisable(true);
        restoreButton.setDisable(false);
        panelStatusLabel.setText("Disparado");

        eventCode = "1130";
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
        eventSender.sendEvent(eventPackage);
    }

    public void restore() {
        Logger.log("Enviando evento de restauração");
        setOffButton.setDisable(false);
        restoreButton.setDisable(true);

        eventCode = "3130";
        EventPackage eventPackage = eventPackageGenerator.generateEvent(eventCode);
        eventSender.sendEvent(eventPackage);
    }

    public void updatePanelConnection(boolean connected) {
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

    @Override
    public void updateArmStatus(boolean armed) {
        if (!armed) {
            panelStatusLabel.setText("Desarmado");
            panelStatusLabel.setTextFill(Colors.GREEN);
            armPanelButton.setText("Armar");
            setOffButton.setDisable(true);
            restoreButton.setDisable(true);
            return;
        }

        panelStatusLabel.setText("Armado");
        panelStatusLabel.setTextFill(Colors.RED);
        armPanelButton.setText("Desarmar");
        setOffButton.setDisable(false);
    }

    private void disableAllButtons() {
        armPanelButton.setDisable(true);
        setOffButton.setDisable(true);
        restoreButton.setDisable(true);
    }

    @FXML
    private void initialize() {
        panelStatusListener.setController(this);
        updatePanelConnection(Panel.isConnected());
        updateArmStatus(Panel.isArmed());
    }

    @Override
    public Pane getRoot() {
        return panelTab;
    }
}
