package br.com.fulltime.fullarm.core.connection.listener;

import br.com.fulltime.fullarm.app.javafx.controller.panel.PanelPaneController;
import br.com.fulltime.fullarm.core.connection.initializer.ConnectionInitializer;
import org.springframework.stereotype.Service;

@Service
public class ConnectionListenerImpl implements ConnectionListener {
    private PanelPaneController controller;

    public ConnectionListenerImpl(ConnectionInitializer initializer) {
        initializer.setConnectionListener(this);
    }

    @Override
    public void onConnect(boolean connected) {
        controller.updateConnectionStatus(connected);
    }

    @Override
    public void setController(PanelPaneController controller) {
        this.controller = controller;
    }
}
