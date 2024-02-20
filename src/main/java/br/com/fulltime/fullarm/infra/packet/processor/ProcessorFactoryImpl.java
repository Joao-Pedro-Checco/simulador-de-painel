package br.com.fulltime.fullarm.infra.packet.processor;

import br.com.fulltime.fullarm.infra.packet.processor.ack.AckProcessor;
import br.com.fulltime.fullarm.infra.packet.processor.command.CommandProcessor;
import br.com.fulltime.fullarm.infra.packet.processor.unknown.UnknownPackageProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessorFactoryImpl implements ProcessorFactory {
    private final AckProcessor ackProcessor;
    private final CommandProcessor commandProcessor;
    private final UnknownPackageProcessor unknownPackageProcessor;
    private final List<PackageProcessor> processorList = new ArrayList<>();

    public ProcessorFactoryImpl(AckProcessor ackProcessor,
                                CommandProcessor commandProcessor,
                                UnknownPackageProcessor unknownPackageProcessor) {
        this.ackProcessor = ackProcessor;
        this.commandProcessor = commandProcessor;
        this.unknownPackageProcessor = unknownPackageProcessor;

        initializeList();
    }

    private void initializeList() {
        processorList.add(ackProcessor);
        processorList.add(commandProcessor);
    }

    @Override
    public PackageProcessor getProcessor(String hexString) {
        return processorList.stream()
                .filter(processor -> processor.canProcess(hexString))
                .findFirst().orElse(unknownPackageProcessor);
    }
}
