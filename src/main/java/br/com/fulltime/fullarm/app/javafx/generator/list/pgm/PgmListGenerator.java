package br.com.fulltime.fullarm.app.javafx.generator.list.pgm;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public interface PgmListGenerator {
    void generateList(ListView<HBox> listView);
}
