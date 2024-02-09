package br.com.fulltime.fullarm.infra.packet;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.infra.HexStringConverter;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Service
public class PackageSenderImpl implements PackageSender {
    private Socket socket;

    @Override
    public void sendPackage(String hexString) {
        try {
            Logger.log("Enviando pacote -> " + hexString);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] packet = HexStringConverter.hexStringToByteArray(hexString);

            dataOutputStream.write(packet);
            dataOutputStream.flush();
        } catch (IOException e) {
            Logger.log("Falha no envio do pacote");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
