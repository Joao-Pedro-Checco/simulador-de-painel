package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand;

public interface SubcommandProcessor {
    void processSubcommand(String subcommand);

    boolean canProcess(String subcommand);
}
