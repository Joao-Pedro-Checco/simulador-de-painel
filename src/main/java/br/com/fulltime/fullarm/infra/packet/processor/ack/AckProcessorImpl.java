package br.com.fulltime.fullarm.infra.packet.processor.ack;

import br.com.fulltime.fullarm.core.connection.timeout.TimeoutHandler;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.packet.constants.PackageIdentifier;
import org.springframework.stereotype.Service;

@Service
public class AckProcessorImpl implements AckProcessor {
    private final TimeoutHandler timeoutHandler;

    public AckProcessorImpl(TimeoutHandler timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public void processPackage(String hexString) {
        Logger.log("Processando pacote ACK");
        if (!Panel.isIsAuthenticated()) {
            timeoutHandler.messageArrived();
        }
    }

    @Override
    public boolean canProcess(String hexString) {
        if (hexString.length() != 2) {
            return false;
        }

        return PackageIdentifier.getByValue(hexString) == PackageIdentifier.ACK;
    }
}
