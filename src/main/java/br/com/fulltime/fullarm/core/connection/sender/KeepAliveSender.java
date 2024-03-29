package br.com.fulltime.fullarm.core.connection.sender;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.keepalive.KeepAlivePackage;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.connection.Connection;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KeepAliveSender implements Runnable {
    private final PackageSender packageSender;

    public KeepAliveSender(PackageSender packageSender) {
        this.packageSender = packageSender;
    }

    @Override
    public void run() {
        try {
            do {
                Logger.log("Enviando pacote de keep-alive");
                packageSender.sendPackage(new KeepAlivePackage());

                long sleepTime = TimeUnit.MINUTES.toMillis(Panel.getKeepAliveTime());
                Thread.sleep(sleepTime);
            } while (!Connection.getSocket().isClosed());
        } catch (InterruptedException e) {
            Logger.log("Thread de keep-alive interrompida");
        }
    }
}
