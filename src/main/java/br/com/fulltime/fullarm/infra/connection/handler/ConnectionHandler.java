package br.com.fulltime.fullarm.infra.connection.handler;

public interface ConnectionHandler {
    void initializeConnection(String host, Integer port);

    void terminateConnection();
}
