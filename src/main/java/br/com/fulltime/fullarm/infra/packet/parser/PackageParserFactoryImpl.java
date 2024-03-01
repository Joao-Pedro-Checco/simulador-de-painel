package br.com.fulltime.fullarm.infra.packet.parser;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.infra.packet.parser.authentication.AuthenticationPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.event.EventPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.keepalive.KeepAlivePackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.status.StatusPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.unknown.UnknownPackageParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageParserFactoryImpl implements PackageParserFactory {
    private final AuthenticationPackageParser authenticationPackageParser;
    private final EventPackageParser eventPackageParser;
    private final KeepAlivePackageParser keepAlivePackageParser;
    private final StatusPackageParser statusPackageParser;
    private final UnknownPackageParser unknownPackageParser;
    private final List<PackageParser> parserList = new ArrayList<>();

    public PackageParserFactoryImpl(AuthenticationPackageParser authenticationPackageParser,
                                    EventPackageParser eventPackageParser,
                                    KeepAlivePackageParser keepAlivePackageParser,
                                    StatusPackageParser statusPackageParser,
                                    UnknownPackageParser unknownPackageParser) {
        this.authenticationPackageParser = authenticationPackageParser;
        this.eventPackageParser = eventPackageParser;
        this.keepAlivePackageParser = keepAlivePackageParser;
        this.statusPackageParser = statusPackageParser;
        this.unknownPackageParser = unknownPackageParser;

        initializeList();
    }

    private void initializeList() {
        parserList.add(authenticationPackageParser);
        parserList.add(eventPackageParser);
        parserList.add(statusPackageParser);
        parserList.add(keepAlivePackageParser);
    }

    @Override
    public PackageParser getParser(GenericPackage genericPackage) {
        return parserList.stream()
                .filter(parser -> parser.canParse(genericPackage))
                .findFirst().orElse(unknownPackageParser);
    }
}
