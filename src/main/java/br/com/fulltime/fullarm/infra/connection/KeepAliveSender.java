package br.com.fulltime.fullarm.infra.connection;

import br.com.fulltime.fullarm.infra.HexStringConverter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

public class KeepAliveSender extends TimerTask {
    private final Socket socket;

    public KeepAliveSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("================{Enviando pacote de keep-alive}================");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] keepAlivePackage = HexStringConverter.hexStringToByteArray("F7");

            dataOutputStream.write(keepAlivePackage);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("================{Falha no envio do pacote}================");
            throw new RuntimeException(e);
        }
    }
}
