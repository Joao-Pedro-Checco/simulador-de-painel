package br.com.fulltime.fullarm.infra.packet.processor.command;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.nack.NackPackage;
import br.com.fulltime.fullarm.core.packet.nack.NackType;
import br.com.fulltime.fullarm.core.panel.Panel;
import br.com.fulltime.fullarm.infra.HexStringFormatter;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
import br.com.fulltime.fullarm.infra.packet.constants.PackageIdentifier;
import br.com.fulltime.fullarm.infra.packet.processor.command.subcommand.SubcommandProcessorFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandProcessorImpl implements CommandProcessor {
    private final SubcommandProcessorFactory subcommandProcessorFactory;
    private final PackageSender packageSender;
    private final List<String> validPasswordCharacters = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");

    public CommandProcessorImpl(SubcommandProcessorFactory subcommandProcessorFactory, PackageSender packageSender) {
        this.subcommandProcessorFactory = subcommandProcessorFactory;
        this.packageSender = packageSender;
    }

    @Override
    public void processPackage(String hexString) {
        Logger.log("Processando pacote de comando");
        List<String> bytes = splitBytes(hexString);
        String commandPassword = getCommandPassword(bytes);

        if (!commandPassword.equals(Panel.getPassword())) {
            Logger.log("Comando com senha inválida");
            packageSender.sendPackage(new NackPackage(NackType.WRONG_PASSWORD));
            return;
        }

        String subcommandBytes = extractSubcommandBytes(bytes, commandPassword.length());
        String subcommand = HexStringFormatter.format(subcommandBytes);
        subcommandProcessorFactory.getProcessor(subcommand).processSubcommand(subcommand);
    }

    @Override
    public List<String> splitBytes(String hexString) {
        return Arrays.asList(hexString.split(" "));
    }

    private List<String> getPasswordBytes(List<String> bytes) {
        if (bytes.size() >= 6) {
            return bytes.subList(0, 6);
        }
        return bytes.subList(0, 4);
    }

    private String getCommandPassword(List<String> bytes) {
        List<String> password = bytes.stream().map(this::parseHexToAscii)
                .filter(validPasswordCharacters::contains)
                .collect(Collectors.toList());

        password = getPasswordBytes(password);
        return String.join("", password);
    }

    private String parseHexToAscii(String passwordByte) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < passwordByte.length(); i += 2) {
            String str = passwordByte.substring(i, i + 2);
            stringBuilder.append((char) Integer.parseInt(str, 16));
        }

        return stringBuilder.toString().trim();
    }

    private String extractSubcommandBytes(List<String> bytes, int passwordSize) {
        int startOfSubcommand = 3 + passwordSize;

        List<String> subcommandBytes = new ArrayList<>();
        for (int i = startOfSubcommand; !bytes.get(i).equals("21"); i++) {
            subcommandBytes.add(bytes.get(i));
        }

        return String.join("", subcommandBytes);
    }

    @Override
    public boolean canProcess(String hexString) {
        List<String> bytes = splitBytes(hexString);
        if (bytes.size() - 2 != Integer.parseInt(bytes.get(0), 16)) {
            return false;
        }

        return PackageIdentifier.getByValue(bytes.get(1)) == PackageIdentifier.COMMAND;
    }
}
