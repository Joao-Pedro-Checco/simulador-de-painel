package br.com.fulltime.fullarm.app.javafx.generator.list.sector;

import br.com.fulltime.fullarm.app.javafx.generator.hbox.sector.SectorHBoxGenerator;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.core.panel.components.Partition;
import br.com.fulltime.fullarm.core.panel.components.Zone;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SectorListGeneratorImpl implements SectorListGenerator {
    private final SectorHBoxGenerator sectorHBoxGenerator;

    public SectorListGeneratorImpl(SectorHBoxGenerator sectorHBoxGenerator) {
        this.sectorHBoxGenerator = sectorHBoxGenerator;
    }

    @Override
    public void generateList(ListView<HBox> listView, Partition partition) {
        if (!listView.getItems().isEmpty()) {
            listView.getItems().clear();
        }

        List<Zone> zones = Panel.isPartitioned() ? partition.getZones() : Panel.getZones();
        zones.forEach(zone -> {
            if (zone.isEnabled()) {
                HBox hBox = sectorHBoxGenerator.generateSector(zone);
                listView.getItems().add(hBox);
            }
        });
    }
}
