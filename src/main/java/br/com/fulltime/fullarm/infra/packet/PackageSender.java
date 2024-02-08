package br.com.fulltime.fullarm.infra.packet;

import java.net.Socket;

public interface PackageSender {
    void sendPackage(String hexString);

    void setSocket(Socket socket);
}
