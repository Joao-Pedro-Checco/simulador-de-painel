package br.com.fulltime.fullarm.core.connection.initializer;

import br.com.fulltime.fullarm.core.connection.authenticator.PanelAuthenticator;
import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;
import br.com.fulltime.fullarm.core.connection.sender.KeepAliveSender;
import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.connection.handler.ConnectionHandler;
import javafx.application.Platform;
import org.springframework.stereotype.Service;

@Service
public class ConnectionInitializerImpl implements ConnectionInitializer {
    private final ConnectionHandler connectionHandler;
    private final PanelAuthenticator panelAuthenticator;
    private final KeepAliveSender keepAliveSender;
    private ConnectionListener connectionListener;

    public ConnectionInitializerImpl(ConnectionHandler connectionHandler,
                                     PanelAuthenticator panelAuthenticator,
                                     KeepAliveSender keepAliveSender) {
        this.connectionHandler = connectionHandler;
        this.panelAuthenticator = panelAuthenticator;
        this.keepAliveSender = keepAliveSender;
    }

    @Override
    public void initializeConnection(String host, Integer port, AuthenticationPackage authenticationPackage) {
        new Thread(() -> {
            connectionHandler.connect(host, port);
            Panel.connected = panelAuthenticator.authenticatePanel(authenticationPackage);

            if (Panel.connected) {
                new Thread(keepAliveSender).start();
            }

            notifyConnected(Panel.connected);
        }).start();
    }

    @Override
    public void setConnectionListener(ConnectionListener listener) {
        connectionListener = listener;
    }

    private void notifyConnected(boolean connected) {
        if (connectionListener != null) {
            Platform.runLater(() -> connectionListener.onConnect(connected));
        }
    }
}
