package br.com.fulltime.fullarm.core.connection.initializer;

import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;
import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;

public interface ConnectionInitializer {
    void initializeConnection(String host, Integer port, AuthenticationPackage authenticationPackage);

    void setConnectionListener(ConnectionListener listener);
}
