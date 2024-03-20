package br.com.fulltime.fullarm.core.panel.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelTabController;

public interface PanelStatusListener {
    void onUpdate();
    void setController(PanelTabController controller);
}
