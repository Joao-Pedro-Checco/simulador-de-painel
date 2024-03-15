package br.com.fulltime.fullarm.app.javafx.generator.list.sector;

import br.com.fulltime.fullarm.core.panel.components.Partition;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public interface SectorListGenerator {
    void generateList(ListView<HBox> listView, Partition partition);
}
