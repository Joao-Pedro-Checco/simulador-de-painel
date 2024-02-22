package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.disarm;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DisarmProcessorImpl implements DisarmProcessor {
    private final PackageSender packageSender;

    public DisarmProcessorImpl(PackageSender packageSender) {
        this.packageSender = packageSender;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de desarme");
        // TODO: desarmar o painel
        // TODO: enviar um ack (02 E9 FE EA)
    }

    @Override
    public List<String> splitBytes(String subcommand) {
        return Arrays.asList(subcommand.split(" "));
    }

    @Override
    public boolean canProcess(String subcommand) {
        List<String> bytes = splitBytes(subcommand);
        if (bytes.size() > 5) {
            return false;
        }

        return SubcommandIdentifier.getByValue(bytes.get(0)) == SubcommandIdentifier.DISARM;
    }
}