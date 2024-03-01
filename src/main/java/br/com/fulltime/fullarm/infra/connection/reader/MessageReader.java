package br.com.fulltime.fullarm.infra.connection.reader;

import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.interpreter.PackageInterpreter;
import br.com.fulltime.fullarm.infra.HexStringFormatter;
import br.com.fulltime.fullarm.infra.connection.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MessageReader extends Thread {
    private InputStream in;
    private final PackageInterpreter packageInterpreter;

    public MessageReader(PackageInterpreter packageInterpreter) {
        this.packageInterpreter = packageInterpreter;
    }

    @Override
    public void run() {
        try {
            in = Connection.socket.getInputStream();
            while (!Connection.socket.isClosed()) {
                byte[] bytes = read();
                if (bytes != null) {
                    String hexString = HexStringFormatter.printHexBinary(bytes);
                    Logger.log("Recebido <- " + hexString);
                    packageInterpreter.interpretPackage(hexString);
                }

                Thread.sleep(16);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Logger.log("Thread de Reader interrompida");
        }
    }

    private byte[] read() throws IOException {
        int available = in.available();
        if (available > 0) {
            byte[] data = new byte[available];
            in.read(data, 0, available);
            return data;
        }
        return null;
    }
}
