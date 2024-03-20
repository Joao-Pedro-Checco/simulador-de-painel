package br.com.fulltime.fullarm.app.javafx.generator.hbox.pgm;

import br.com.fulltime.fullarm.app.javafx.Colors;
import br.com.fulltime.fullarm.core.panel.components.Pgm;
import br.com.fulltime.fullarm.core.panel.handler.PanelHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class PgmHBoxGeneratorImpl implements PgmHBoxGenerator {
    private final PanelHandler panelHandler;
    private final String onButtonStyle = "-fx-background-color: rgba(99,99,255,0.7)";
    private final String offButtonStyle = "-fx-background-color: rgba(128,128,128,0.7)";

    public PgmHBoxGeneratorImpl(PanelHandler panelHandler) {
        this.panelHandler = panelHandler;
    }

    @Override
    public HBox generatePgm(Pgm pgm) {
        HBox hBox = new HBox();
        Button onOffButton = getButton(pgm);

        int pgmNumber = pgm.getPgmNumber();
        String text = "PGM " + (pgmNumber <= 9 ? "0" + pgmNumber : pgmNumber);

        onOffButton.setOnAction(event -> togglePgmOnOff(pgm, onOffButton));
        hBox.getChildren().addAll(new Text(text), onOffButton);
        hBox.setSpacing(90);

        return hBox;
    }

    private Button getButton(Pgm pgm) {
        Button button = new Button(pgm.isTurnedOn() ? "Desligar" : "Ligar");
        button.setTextFill(Colors.WHITE);

        if (!pgm.isTurnedOn()) {
            button.setStyle(onButtonStyle);
            button.setTextFill(Colors.WHITE);
            return button;
        }

        button.setStyle(offButtonStyle);
        return button;
    }

    private void togglePgmOnOff(Pgm pgm, Button onOffButton) {
        if (!pgm.isTurnedOn()) {
            panelHandler.turnPgmOn(pgm);
            onOffButton.setText("Desligar");
            onOffButton.setStyle(offButtonStyle);
            return;
        }

        panelHandler.turnPgmOff(pgm);
        onOffButton.setText("Ligar");
        onOffButton.setTextFill(Colors.WHITE);
        onOffButton.setStyle(onButtonStyle);
    }
}
