package br.com.fulltime.fullarm.core.connection.listener;

import br.com.fulltime.fullarm.app.javafx.controller.connection.ConnectionPaneController;

public interface ConnectionListener {
    void onConnect(boolean connected);

    void setConnectionController(ConnectionPaneController connectionController);
}
