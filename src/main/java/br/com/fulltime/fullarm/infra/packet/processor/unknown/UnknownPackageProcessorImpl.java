package br.com.fulltime.fullarm.infra.packet.processor.unknown;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.nack.NackPackage;
import br.com.fulltime.fullarm.core.packet.nack.NackType;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

@Service
public class UnknownPackageProcessorImpl implements UnknownPackageProcessor {
    private final PackageSender packageSender;

    public UnknownPackageProcessorImpl(PackageSender packageSender) {
        this.packageSender = packageSender;
    }

    @Override
    public void processPackage(String hexString) {
        Logger.log("O pacote recebido não foi identificado");
        packageSender.sendPackage(new NackPackage(NackType.INVALID_PACKAGE_FORMAT));
    }

    @Override
    public boolean canProcess(String hexString) {
        return true;
    }
}
