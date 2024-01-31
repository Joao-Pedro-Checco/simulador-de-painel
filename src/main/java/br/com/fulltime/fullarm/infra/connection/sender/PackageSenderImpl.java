package br.com.fulltime.fullarm.infra.connection.sender;

import br.com.fulltime.fullarm.infra.connection.handler.ConnectionHandler;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Service
public class PackageSenderImpl implements PackageSender {
    private final ConnectionHandler connectionHandler;

    public PackageSenderImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void sendPackage(byte[] packet) {
        try {
            Socket socket = connectionHandler.getSocket();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.write(packet);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("================{Falha ao enviar o pacote}================");
            throw new RuntimeException(e);
        }
    }
}
