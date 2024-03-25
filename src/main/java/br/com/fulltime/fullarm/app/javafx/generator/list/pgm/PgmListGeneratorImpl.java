package br.com.fulltime.fullarm.app.javafx.generator.list.pgm;

import br.com.fulltime.fullarm.app.javafx.generator.hbox.pgm.PgmHBoxGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class PgmListGeneratorImpl implements PgmListGenerator {
    private final PgmHBoxGenerator pgmHBoxGenerator;

    public PgmListGeneratorImpl(PgmHBoxGenerator pgmHBoxGenerator) {
        this.pgmHBoxGenerator = pgmHBoxGenerator;
    }

    @Override
    public void generateList(ListView<HBox> listView) {
        if (!listView.getItems().isEmpty()) {
            listView.getItems().clear();
        }

        Panel.getPgmList().forEach(pgm -> {
            HBox hBox = pgmHBoxGenerator.generatePgm(pgm);
            listView.getItems().add(hBox);
        });
    }
}
