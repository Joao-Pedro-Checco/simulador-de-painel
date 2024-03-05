package br.com.fulltime.fullarm.core.panel.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelTabController;

public interface PanelStatusListener {
    void onArm();
    void onDisarm();
    void setController(PanelTabController controller);
}
