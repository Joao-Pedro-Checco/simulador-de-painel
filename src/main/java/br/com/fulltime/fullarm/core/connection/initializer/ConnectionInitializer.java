package br.com.fulltime.fullarm.core.connection.initializer;

import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;

public interface ConnectionInitializer {
    void initializeConnection(String host, Integer port, String connectionType, String account, String macAddress);

    void setConnectionListener(ConnectionListener listener);
}
