package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand;

import br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.arm.ArmProcessor;
import br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.bypass.BypassProcessor;
import br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.disarm.DisarmProcessor;
import br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.status.StatusRequestProcessor;
import br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.unknown.UnknownSubcommandProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubcommandProcessorFactoryImpl implements SubcommandProcessorFactory {
    private final ArmProcessor armProcessor;
    private final BypassProcessor bypassProcessor;
    private final DisarmProcessor disarmProcessor;
    private final StatusRequestProcessor statusRequestProcessor;
    private final UnknownSubcommandProcessor unknownSubcommandProcessor;
    private final List<SubcommandProcessor> processorList = new ArrayList<>();

    public SubcommandProcessorFactoryImpl(ArmProcessor armProcessor,
                                          BypassProcessor bypassProcessor,
                                          DisarmProcessor disarmProcessor,
                                          StatusRequestProcessor statusRequestProcessor,
                                          UnknownSubcommandProcessor unknownSubcommandProcessor) {
        this.armProcessor = armProcessor;
        this.bypassProcessor = bypassProcessor;
        this.disarmProcessor = disarmProcessor;
        this.statusRequestProcessor = statusRequestProcessor;
        this.unknownSubcommandProcessor = unknownSubcommandProcessor;

        initializeList();
    }

    private void initializeList() {
        processorList.add(armProcessor);
        processorList.add(bypassProcessor);
        processorList.add(disarmProcessor);
        processorList.add(statusRequestProcessor);
    }

    @Override
    public SubcommandProcessor getProcessor(String subcommand) {
        return processorList.stream()
                .filter(processor -> processor.canProcess(subcommand))
                .findFirst().orElse(unknownSubcommandProcessor);
    }
}
