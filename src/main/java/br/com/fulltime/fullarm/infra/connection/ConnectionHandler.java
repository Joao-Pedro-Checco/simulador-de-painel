package br.com.fulltime.fullarm.infra.connection;

import br.com.fulltime.fullarm.infra.HexStringConverter;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Service
public class ConnectionHandler {
    private Socket socket;

    public void createConnection(String host, Integer port) {
        try {
            System.out.println("================{Conectando ao Servidor}================");
            socket = new Socket(host, port);

            System.out.println("================{Enviando pacote de autenticação}================");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] authenticationPackage = HexStringConverter.hexStringToByteArray("07 94 47 12 34 28 05 01 21");

            dataOutputStream.write(authenticationPackage);
            dataOutputStream.flush();

            long twoMinutesInMilliseconds = TimeUnit.MINUTES.toMillis(2);
            new Timer().scheduleAtFixedRate(new KeepAliveSender(socket), twoMinutesInMilliseconds, twoMinutesInMilliseconds);
        } catch (IOException e) {
            System.out.printf("================{Falha na conexão (host: %s | port: %d)}================\n", host, port);
            throw new RuntimeException(e);
        }
    }

    public void listenForMessage() {
        new MessageReader(socket).run();
    }
}
