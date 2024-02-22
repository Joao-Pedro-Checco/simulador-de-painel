package br.com.fulltime.fullarm.core.connection.initializer;

import br.com.fulltime.fullarm.core.connection.authenticator.PanelAuthenticator;
import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.generator.authentication.AuthenticationPackageGenerator;
import br.com.fulltime.fullarm.infra.connection.handler.ConnectionHandler;
import javafx.application.Platform;
import org.springframework.stereotype.Service;

@Service
public class ConnectionInitializerImpl implements ConnectionInitializer {
    private final ConnectionHandler connectionHandler;
    private final AuthenticationPackageGenerator authenticationPackageGenerator;
    private final PanelAuthenticator panelAuthenticator;
    private ConnectionListener connectionListener;

    public ConnectionInitializerImpl(ConnectionHandler connectionHandler,
                                     AuthenticationPackageGenerator authenticationPackageGenerator,
                                     PanelAuthenticator panelAuthenticator) {
        this.connectionHandler = connectionHandler;
        this.authenticationPackageGenerator = authenticationPackageGenerator;
        this.panelAuthenticator = panelAuthenticator;
    }

    @Override
    public void initializeConnection(String host, Integer port, String connectionType, String account, String macAddress) {
        new Thread(() -> {
            connectionHandler.connect(host, port);
            String authenticationPackage = authenticationPackageGenerator.generatePackage(connectionType, account, macAddress);

            Logger.log(String.format("Enviando pacote de autenticação (conta: %s | mac: %s)", account, macAddress));
            boolean connected = panelAuthenticator.authenticatePanel(authenticationPackage);

            notifyConnected(connected);
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
