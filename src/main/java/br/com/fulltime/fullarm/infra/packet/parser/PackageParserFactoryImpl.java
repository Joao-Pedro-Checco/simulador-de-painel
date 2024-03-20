package br.com.fulltime.fullarm.infra.packet.parser;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.infra.packet.parser.ack.AckPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.authentication.AuthenticationPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.event.EventPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.keepalive.KeepAlivePackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.nack.NackPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.status.StatusPackageParser;
import br.com.fulltime.fullarm.infra.packet.parser.unknown.UnknownPackageParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageParserFactoryImpl implements PackageParserFactory {
    private final AckPackageParser ackPackageParser;
    private final AuthenticationPackageParser authenticationPackageParser;
    private final EventPackageParser eventPackageParser;
    private final KeepAlivePackageParser keepAlivePackageParser;
    private final NackPackageParser nackPackageParser;
    private final StatusPackageParser statusPackageParser;
    private final UnknownPackageParser unknownPackageParser;
    private final List<PackageParser> parserList = new ArrayList<>();

    public PackageParserFactoryImpl(AckPackageParser ackPackageParser,
                                    AuthenticationPackageParser authenticationPackageParser,
                                    EventPackageParser eventPackageParser,
                                    KeepAlivePackageParser keepAlivePackageParser,
                                    NackPackageParser nackPackageParser,
                                    StatusPackageParser statusPackageParser,
                                    UnknownPackageParser unknownPackageParser) {
        this.ackPackageParser = ackPackageParser;
        this.authenticationPackageParser = authenticationPackageParser;
        this.eventPackageParser = eventPackageParser;
        this.keepAlivePackageParser = keepAlivePackageParser;
        this.nackPackageParser = nackPackageParser;
        this.statusPackageParser = statusPackageParser;
        this.unknownPackageParser = unknownPackageParser;

        initializeList();
    }

    private void initializeList() {
        parserList.add(ackPackageParser);
        parserList.add(authenticationPackageParser);
        parserList.add(eventPackageParser);
        parserList.add(keepAlivePackageParser);
        parserList.add(nackPackageParser);
        parserList.add(statusPackageParser);
    }

    @Override
    public PackageParser getParser(GenericPackage genericPackage) {
        return parserList.stream()
                .filter(parser -> parser.canParse(genericPackage))
                .findFirst().orElse(unknownPackageParser);
    }
}
