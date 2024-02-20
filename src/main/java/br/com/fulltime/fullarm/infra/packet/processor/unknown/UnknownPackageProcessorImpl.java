package br.com.fulltime.fullarm.infra.packet.processor.unknown;

import br.com.fulltime.fullarm.core.logger.Logger;
import org.springframework.stereotype.Service;

@Service
public class UnknownPackageProcessorImpl implements UnknownPackageProcessor {
    @Override
    public void processPackage(String hexString) {
        Logger.log("O pacote recebido não foi identificado");
    }

    @Override
    public boolean canProcess(String hexString) {
        return true;
    }
}
