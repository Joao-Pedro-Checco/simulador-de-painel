package br.com.fulltime.fullarm.core.packet.interpreter;

import br.com.fulltime.fullarm.infra.HexStringFormatter;
import br.com.fulltime.fullarm.infra.packet.processor.ProcessorFactory;
import org.springframework.stereotype.Service;

@Service
public class PackageInterpreterImpl implements PackageInterpreter {
    private final ProcessorFactory processorFactory;

    public PackageInterpreterImpl(ProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    @Override
    public void interpretPackage(String hexString) {
        String formattedPackage = HexStringFormatter.format(hexString);
        processorFactory.getProcessor(formattedPackage).processPackage(formattedPackage);
    }
}
