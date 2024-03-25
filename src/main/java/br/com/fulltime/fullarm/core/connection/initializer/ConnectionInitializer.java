package br.com.fulltime.fullarm.core.connection.initializer;

import br.com.fulltime.fullarm.core.connection.listener.ConnectionListener;

public interface ConnectionInitializer {
    void initializeConnection();

    void setConnectionListener(ConnectionListener listener);
}
