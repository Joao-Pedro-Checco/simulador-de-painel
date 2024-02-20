package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand;

public interface SubcommandProcessorFactory {
    SubcommandProcessor getProcessor(String subcommand);
}
