package br.com.fulltime.fullarm.core.connection.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelPaneController;

public interface ConnectionListener {
    void onConnect(boolean connected);

    void setController(PanelPaneController connectionController);
}
