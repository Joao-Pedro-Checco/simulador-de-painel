package br.com.fulltime.fullarm.core.connection.listener;

import br.com.fulltime.fullarm.app.javafx.controller.connection.ConnectionTabController;

public interface ConnectionListener {
    void onConnect(boolean connected);

    void setConnectionController(ConnectionTabController connectionController);
}
