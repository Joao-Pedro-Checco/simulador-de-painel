package br.com.fulltime.fullarm.infra.connection.handler;

public interface ConnectionHandler {
    void connect(String host, Integer port);

    void disconnect();
}
