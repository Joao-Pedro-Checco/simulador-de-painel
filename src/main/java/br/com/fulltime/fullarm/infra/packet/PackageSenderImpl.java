package br.com.fulltime.fullarm.infra.packet;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.infra.HexStringConverter;
import br.com.fulltime.fullarm.infra.connection.Connection;
import br.com.fulltime.fullarm.infra.packet.parser.PackageParserFactory;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;

@Service
public class PackageSenderImpl implements PackageSender {
    private final PackageParserFactory packageParserFactory;

    public PackageSenderImpl(PackageParserFactory packageParserFactory) {
        this.packageParserFactory = packageParserFactory;
    }

    @Override
    public void sendPackage(GenericPackage genericPackage) {
        try {
            String hexString = packageParserFactory.getParser(genericPackage).parsePackage(genericPackage);
            Logger.log("Enviando pacote -> " + hexString);
            byte[] packet = HexStringConverter.hexStringToByteArray(hexString);

            DataOutputStream dataOutputStream = new DataOutputStream(Connection.getSocket().getOutputStream());
            dataOutputStream.write(packet);
            dataOutputStream.flush();
        } catch (IOException e) {
            Logger.log("Falha no envio do pacote");
            throw new RuntimeException(e);
        }
    }
}
