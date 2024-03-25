package br.com.fulltime.fullarm.core.panel.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelPaneController;

public interface PanelStatusListener {
    void onUpdate();
    void setController(PanelPaneController controller);
}
