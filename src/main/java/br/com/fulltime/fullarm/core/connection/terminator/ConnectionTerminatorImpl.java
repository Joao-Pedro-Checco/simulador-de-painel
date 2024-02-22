package br.com.fulltime.fullarm.core.connection.terminator;

import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.connection.disconnection.DisconnectionHandler;
import org.springframework.stereotype.Service;

@Service
public class ConnectionTerminatorImpl implements ConnectionTerminator {
    private final DisconnectionHandler disconnectionHandler;

    public ConnectionTerminatorImpl(DisconnectionHandler disconnectionHandler) {
        this.disconnectionHandler = disconnectionHandler;
    }

    @Override
    public void terminateConnection() {
        disconnectionHandler.disconnect();
        Panel.connected = false;
    }
}
