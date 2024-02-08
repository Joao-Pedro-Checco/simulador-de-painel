package br.com.fulltime.fullarm.infra.connection.reader;

import br.com.fulltime.fullarm.core.connection.timeout.TimeoutHandler;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.infra.connection.ConnectionStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MessageReader extends Thread {
    private final Socket socket;
    private InputStream in;
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
    private final TimeoutHandler timeoutHandler;

    public MessageReader(Socket socket, TimeoutHandler timeoutHandler) {
        this.socket = socket;
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public void run() {
        try {
            in = socket.getInputStream();
            while (socket.isConnected()) {
                byte[] bytes = read();
                if (bytes != null) {
                    String hexString = printHexBinary(bytes);
                    System.out.println("Recebido -> " + hexString);
                    if (hexString.equals("FE") && !ConnectionStatus.isAuthenticated) {
                        timeoutHandler.messageArrived();
                    }
                }

                Thread.sleep(16);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Logger.log("Thread de Reader interrompida");
        }
    }

    private String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
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
