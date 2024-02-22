package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.status;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.PanelStatus;
import br.com.fulltime.fullarm.core.packet.generator.status.PanelStatusGenerator;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import org.springframework.stereotype.Service;

@Service
public class StatusRequestProcessorImpl implements StatusRequestProcessor {
    private final PanelStatusGenerator panelStatusGenerator;
    private final PackageSender packageSender;

    public StatusRequestProcessorImpl(PanelStatusGenerator panelStatusGenerator,
                                      PackageSender packageSender) {
        this.panelStatusGenerator = panelStatusGenerator;
        this.packageSender = packageSender;
    }

    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de solicitação de status");
        PanelStatus panelStatus = panelStatusGenerator.generateStatus();

        // TODO: parsear o status para bytes
    }

    @Override
    public boolean canProcess(String subcommand) {
        if (subcommand.length() != 2) {
            return false;
        }

        return SubcommandIdentifier.getByValue(subcommand) == SubcommandIdentifier.STATUS_REQUEST;
    }
}
