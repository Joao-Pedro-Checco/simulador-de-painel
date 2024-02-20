package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.status;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.status.PanelStatus;
import br.com.fulltime.fullarm.core.packet.status.PanelStatusGenerator;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.SubcommandIdentifier;
import org.springframework.stereotype.Service;

@Service
public class StatusRequestProcessorImpl implements StatusRequestProcessor {
    @Override
    public void processSubcommand(String subcommand) {
        Logger.log("Processando comando de solicitação de status");
        // TODO: montar e enviar um pacote de status completo
    }

    @Override
    public boolean canProcess(String subcommand) {
        if (subcommand.length() != 2) {
            return false;
        }

        return SubcommandIdentifier.getByValue(subcommand) == SubcommandIdentifier.STATUS_REQUEST;
    }
}
