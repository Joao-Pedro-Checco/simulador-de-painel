package br.com.fulltime.fullarm.infra.connection.handler;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.interpreter.PackageInterpreter;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.connection.Connection;
import br.com.fulltime.fullarm.infra.connection.reader.MessageReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
public class ConnectionHandlerImpl implements ConnectionHandler {
    private MessageReader messageReader;
    private final PackageInterpreter packageInterpreter;

    public ConnectionHandlerImpl(PackageInterpreter packageInterpreter) {
        this.packageInterpreter = packageInterpreter;
    }

    @Override
    public void connect(String host, Integer port) {
        try {
            Panel.isAuthenticated = false;
            Logger.log(String.format("Conectando ao servidor (host: %s | port: %d)", host, port));
            Connection.socket = new Socket(host, port);

            startListener();
        } catch (IOException e) {
            String logMessage = String.format("Falha na conexão (host: %s | port: %d)", host, port);
            Logger.log(logMessage);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            Panel.isAuthenticated = false;

            if (messageReader != null) {
                messageReader.interrupt();
            }

            if (Connection.socket != null) {
                Logger.log("Encerrando conexão");
                Connection.socket.close();
            }
        } catch (IOException e) {
            Logger.log("Falha ao desconectar");
            throw new RuntimeException(e);
        }
    }

    private void startListener() {
        messageReader = new MessageReader(packageInterpreter);
        messageReader.start();
    }
}
