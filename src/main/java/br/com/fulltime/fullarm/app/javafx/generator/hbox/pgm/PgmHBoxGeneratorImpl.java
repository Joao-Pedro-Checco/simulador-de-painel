package br.com.fulltime.fullarm.app.javafx.generator.hbox.pgm;

import br.com.fulltime.fullarm.core.panel.components.Pgm;
import br.com.fulltime.fullarm.core.panel.handler.PanelHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class PgmHBoxGeneratorImpl implements PgmHBoxGenerator {
    private final PanelHandler panelHandler;

    public PgmHBoxGeneratorImpl(PanelHandler panelHandler) {
        this.panelHandler = panelHandler;
    }

    @Override
    public HBox generatePgm(Pgm pgm) {
        HBox hBox = new HBox();
        Button onOffButton = new Button(pgm.isTurnedOn() ? "Desligar" : "Ligar");

        int pgmNumber = pgm.getPgmNumber();
        String text = "PGM " + (pgmNumber <= 9 ? "0" + pgmNumber : pgmNumber);

        onOffButton.setOnAction(event -> togglePgmOnOff(pgm, onOffButton));
        hBox.getChildren().addAll(new Text(text), onOffButton);
        hBox.setSpacing(220);

        return hBox;
    }

    private void togglePgmOnOff(Pgm pgm, Button onOffButton) {
        if (!pgm.isTurnedOn()) {
            panelHandler.turnPgmOn(pgm);
            onOffButton.setText("Desligar");
            return;
        }

        panelHandler.turnPgmOff(pgm);
        onOffButton.setText("Ligar");
    }
}
