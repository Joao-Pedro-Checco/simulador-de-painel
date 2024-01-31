package br.com.fulltime.fullarm.core.connection.initializer;

public interface ConnectionInitializer {
    void initializeConnection(String host, Integer port, String connectionType, String account, String macAddress);
}
