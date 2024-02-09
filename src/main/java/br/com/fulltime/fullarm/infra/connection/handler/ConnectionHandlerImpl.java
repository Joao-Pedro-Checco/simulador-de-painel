package br.com.fulltime.fullarm.infra.connection.handler;

import br.com.fulltime.fullarm.core.connection.timeout.TimeoutHandler;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.infra.connection.ConnectionStatus;
import br.com.fulltime.fullarm.infra.connection.reader.MessageReader;
import br.com.fulltime.fullarm.infra.connection.sender.KeepAliveSender;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
public class ConnectionHandlerImpl implements ConnectionHandler {
    private Socket socket;
    private KeepAliveSender keepAliveSender;
    private MessageReader messageReader;
    private final TimeoutHandler timeoutHandler;
    private final PackageSender packageSender;

    public ConnectionHandlerImpl(TimeoutHandler timeoutHandler, PackageSender packageSender) {
        this.timeoutHandler = timeoutHandler;
        this.packageSender = packageSender;
    }

    @Override
    public void connect(String host, Integer port) {
        try {
            ConnectionStatus.isAuthenticated = false;
            Logger.log(String.format("Conectando ao servidor (host: %s | port: %d)", host, port));
            socket = new Socket(host, port);
            packageSender.setSocket(socket);

            startListener();
            startKeepAliveSender();
        } catch (IOException e) {
            String logMessage = String.format("Falha na conexão (host: %s | port: %d)", host, port);
            Logger.log(logMessage);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            ConnectionStatus.isAuthenticated = false;

            if (messageReader != null) {
                messageReader.interrupt();
            }

            if (keepAliveSender != null) {
                keepAliveSender.interrupt();
            }

            if (socket != null) {
                Logger.log("Encerrando conexão");
                socket.close();
            }
        } catch (IOException e) {
            Logger.log("Falha ao desconectar");
            throw new RuntimeException(e);
        }
    }

    private void startKeepAliveSender() {
        keepAliveSender = new KeepAliveSender(packageSender, socket);
        keepAliveSender.start();
    }

    private void startListener() {
        messageReader = new MessageReader(socket, timeoutHandler);
        messageReader.start();
    }
}
