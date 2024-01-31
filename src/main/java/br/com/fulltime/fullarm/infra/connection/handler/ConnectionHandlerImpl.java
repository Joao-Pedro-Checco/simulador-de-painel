package br.com.fulltime.fullarm.infra.connection.handler;

import br.com.fulltime.fullarm.core.connection.timeout.TimeoutHandler;
import br.com.fulltime.fullarm.infra.connection.reader.MessageReader;
import br.com.fulltime.fullarm.infra.connection.sender.KeepAliveSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
public class ConnectionHandlerImpl implements ConnectionHandler {
    public static boolean connected;
    private Socket socket;
    private KeepAliveSender keepAliveSender;
    private MessageReader messageReader;
    private final TimeoutHandler timeoutHandler;

    public ConnectionHandlerImpl(TimeoutHandler timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public void connect(String host, Integer port) {
        try {
            System.out.println("================{Conectando ao Servidor}================");
            connected = false;
            socket = new Socket(host, port);

            startListener();
            startKeepAliveSender();
        } catch (IOException e) {
            System.out.printf("================{Falha na conexão (host: %s | port: %d)}================\n", host, port);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            connected = false;

            if (messageReader != null) {
                messageReader.interrupt();
            }

            if (keepAliveSender != null) {
                keepAliveSender.interrupt();
            }

            if (socket != null) {
                System.out.println("================{Encerrando conexão}================");
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("================{Falha ao desconectar}================");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    private void startKeepAliveSender() {
        keepAliveSender = new KeepAliveSender(socket);
        keepAliveSender.start();
    }

    private void startListener() {
        messageReader = new MessageReader(socket, timeoutHandler);
        messageReader.start();
    }
}
