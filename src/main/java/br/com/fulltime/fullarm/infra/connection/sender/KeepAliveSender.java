package br.com.fulltime.fullarm.infra.connection.sender;

import br.com.fulltime.fullarm.infra.HexStringConverter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class KeepAliveSender extends Thread {
    private final Socket socket;

    public KeepAliveSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (socket.isConnected()) {
                long sleepTime = TimeUnit.MINUTES.toMillis(1);
                Thread.sleep(sleepTime);

                System.out.println("================{Enviando pacote de keep-alive}================");
                byte[] keepAlivePackage = HexStringConverter.hexStringToByteArray("F7");
                dataOutputStream.write(keepAlivePackage);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("================{Falha no envio do pacote}================");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("================{Thread de Keep-Alive interrompida}================");
        }
    }
}
