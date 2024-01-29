package br.com.fulltime.fullarm.infra.connection.handler;

import br.com.fulltime.fullarm.infra.HexStringConverter;
import br.com.fulltime.fullarm.infra.connection.reader.MessageReader;
import br.com.fulltime.fullarm.infra.connection.sender.KeepAliveSender;
import br.com.fulltime.fullarm.infra.connection.timeout.TimeoutHandler;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Service
public class ConnectionHandlerImpl implements ConnectionHandler {
    private Socket socket;
    private KeepAliveSender keepAliveSender;
    private MessageReader messageReader;
    private final TimeoutHandler timeoutHandler;

    public ConnectionHandlerImpl(TimeoutHandler timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public void initializeConnection(String host, Integer port) {
        try {
            System.out.println("================{Conectando ao Servidor}================");
            socket = new Socket(host, port);

            System.out.println("================{Enviando pacote de autenticação}================");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] authenticationPackage = HexStringConverter.hexStringToByteArray("07 94 47 12 34 28 05 01 21");
            dataOutputStream.write(authenticationPackage);
            dataOutputStream.flush();

            System.out.println("================{Aguardando resposta do servidor}================");
            timeoutHandler.initializeTimeout(30);
            listenForMessage();

            keepAliveSender = new KeepAliveSender(socket);
            keepAliveSender.start();
        } catch (IOException e) {
            System.out.printf("================{Falha na conexão (host: %s | port: %d)}================\n", host, port);
            throw new RuntimeException(e);
        }
    }

    private void listenForMessage() {
        messageReader = new MessageReader(socket, timeoutHandler);
        messageReader.start();
    }

    @Override
    public void terminateConnection() {
        try {
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
}
