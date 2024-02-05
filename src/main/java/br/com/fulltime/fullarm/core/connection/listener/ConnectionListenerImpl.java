package br.com.fulltime.fullarm.core.connection.listener;

import br.com.fulltime.fullarm.app.javafx.controller.connection.ConnectionTabController;
import br.com.fulltime.fullarm.core.connection.initializer.ConnectionInitializer;
import org.springframework.stereotype.Service;

@Service
public class ConnectionListenerImpl implements ConnectionListener {
    private ConnectionTabController connectionController;

    public ConnectionListenerImpl(ConnectionInitializer initializer) {
        initializer.setConnectionListener(this);
    }

    @Override
    public void onConnect(boolean connected) {
        connectionController.updateConnectionStatus(connected);
    }

    @Override
    public void setConnectionController(ConnectionTabController controller) {
        connectionController = controller;
    }
}