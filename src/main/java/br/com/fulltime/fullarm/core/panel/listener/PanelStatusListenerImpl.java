package br.com.fulltime.fullarm.core.panel.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelPaneController;
import org.springframework.stereotype.Service;

@Service
public class PanelStatusListenerImpl implements PanelStatusListener {
    private PanelPaneController controller;


    @Override
    public void onUpdate() {
        if (controller != null) {
            controller.updateInterface();
        }
    }

    @Override
    public void setController(PanelPaneController controller) {
        this.controller = controller;
    }
}
