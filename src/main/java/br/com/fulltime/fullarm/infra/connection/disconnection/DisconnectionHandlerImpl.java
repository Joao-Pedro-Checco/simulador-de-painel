package br.com.fulltime.fullarm.infra.connection.disconnection;

import br.com.fulltime.fullarm.infra.connection.handler.ConnectionHandler;
import org.springframework.stereotype.Service;

@Service
public class DisconnectionHandlerImpl implements DisconnectionHandler {
    private final ConnectionHandler connectionHandler;

    public DisconnectionHandlerImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void disconnect() {
        connectionHandler.disconnect();
    }
}
