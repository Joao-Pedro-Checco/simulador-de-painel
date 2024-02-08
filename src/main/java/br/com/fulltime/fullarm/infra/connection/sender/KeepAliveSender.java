package br.com.fulltime.fullarm.infra.connection.sender;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.infra.packet.PackageSender;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class KeepAliveSender extends Thread {
    private final PackageSender packageSender;
    private final Socket socket;

    public KeepAliveSender(PackageSender packageSender, Socket socket) {
        this.packageSender = packageSender;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                long sleepTime = TimeUnit.MINUTES.toMillis(1);
                Thread.sleep(sleepTime);

                Logger.log("Enviando pacote de keep-alive");
                packageSender.sendPackage("F7");
            }
        } catch (InterruptedException e) {
            Logger.log("Thread de keep-alive interrompida");
        }
    }
}
