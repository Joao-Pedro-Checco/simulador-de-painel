package br.com.fulltime.fullarm.infra.packet.processor.command.subcommand;

import java.util.List;

public interface LongSubcommandProcessor extends SubcommandProcessor {
    List<String> splitBytes(String subcommand);
}
