package br.com.fulltime.fullarm.core.connection.timeout;

import br.com.fulltime.fullarm.infra.connection.disconnection.DisconnectionHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TimeoutHandlerImpl implements TimeoutHandler {
    private final DisconnectionHandler disconnectionHandler;
    private boolean messageArrived;

    public TimeoutHandlerImpl(@Lazy DisconnectionHandler disconnectionHandler) {
        this.disconnectionHandler = disconnectionHandler;
    }

    @Override
    public void messageArrived() {
        messageArrived = true;
    }

    @Override
    public boolean initializeTimeout(long timeoutSeconds) {

        messageArrived = false;
        long initialTime = System.currentTimeMillis();
        long timeout = initialTime + (timeoutSeconds * 1000);

        while (System.currentTimeMillis() < timeout) {
            if (messageArrived) {
                System.out.println("================{Autenticado com sucesso}================");
                return true;
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                System.out.println("================{Thread de timeout interrompida}================");
            }
        }

        System.out.println("================{Falha ao conectar com o servidor. Cancelando operação...}================");
        disconnectionHandler.disconnect();

        return false;
    }
}
