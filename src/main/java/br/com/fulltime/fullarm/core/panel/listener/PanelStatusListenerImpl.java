package br.com.fulltime.fullarm.core.panel.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelTabController;
import br.com.fulltime.fullarm.core.panel.Panel;
import org.springframework.stereotype.Service;

@Service
public class PanelStatusListenerImpl implements PanelStatusListener {
    private PanelTabController controller;

    @Override
    public void onArm() {
        if (controller != null) {
            controller.updateArmStatus(Panel.isArmed());
        }
    }

    @Override
    public void onDisarm() {
        if (controller != null) {
            controller.updateArmStatus(Panel.isArmed());
        }
    }

    @Override
    public void setController(PanelTabController controller) {
        this.controller = controller;
    }
}
