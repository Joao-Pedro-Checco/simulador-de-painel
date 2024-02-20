package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.unknown;

import br.com.fulltime.fullarm.core.logger.Logger;
import org.springframework.stereotype.Service;

@Service
public class UnknownSubcommandProcessorImpl implements UnknownSubcommandProcessor {
    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("O comando recebido não foi identificado");
    }

    @Override
    public boolean canProcess(String subcommand) {
        return true;
    }
}
