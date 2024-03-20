package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.unknown;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.nack.NackPackage;
import br.com.fulltime.fullarm.core.packet.nack.NackType;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import org.springframework.stereotype.Service;

@Service
public class UnknownSubcommandProcessorImpl implements UnknownSubcommandProcessor {
    private final PackageSender packageSender;

    public UnknownSubcommandProcessorImpl(PackageSender packageSender) {
        this.packageSender = packageSender;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("O comando recebido não foi identificado");
        packageSender.sendPackage(new NackPackage(NackType.INVALID_COMMAND));
    }

    @Override
    public boolean canProcess(String subcommand) {
        return true;
    }
}
