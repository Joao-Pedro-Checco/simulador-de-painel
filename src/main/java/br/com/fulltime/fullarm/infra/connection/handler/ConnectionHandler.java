package br.com.fulltime.fullarm.infra.connection.handler;

import java.net.Socket;

public interface ConnectionHandler {
    void connect(String host, Integer port);

    void disconnect();

    Socket getSocket();
}
