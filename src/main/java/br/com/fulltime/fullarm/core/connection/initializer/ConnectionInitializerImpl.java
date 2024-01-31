package br.com.fulltime.fullarm.core.connection.initializer;

import br.com.fulltime.fullarm.core.connection.authenticator.PanelAuthenticator;
import br.com.fulltime.fullarm.core.packet.AuthenticationPackageGenerator;
import br.com.fulltime.fullarm.infra.connection.handler.ConnectionHandler;
import org.springframework.stereotype.Service;

@Service
public class ConnectionInitializerImpl implements ConnectionInitializer {
    private final ConnectionHandler connectionHandler;
    private final AuthenticationPackageGenerator authenticationPackageGenerator;
    private final PanelAuthenticator panelAuthenticator;

    public ConnectionInitializerImpl(ConnectionHandler connectionHandler,
                                     AuthenticationPackageGenerator authenticationPackageGenerator,
                                     PanelAuthenticator panelAuthenticator) {
        this.connectionHandler = connectionHandler;
        this.authenticationPackageGenerator = authenticationPackageGenerator;
        this.panelAuthenticator = panelAuthenticator;
    }

    @Override
    public void initializeConnection(String host, Integer port, String connectionType, String account, String macAddress) {
        connectionHandler.connect(host, port);
        byte[] authenticationPackage = authenticationPackageGenerator.generatePackage(connectionType, account, macAddress);
        panelAuthenticator.authenticatePanel(authenticationPackage);
    }
}
