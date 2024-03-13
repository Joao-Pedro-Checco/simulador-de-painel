package br.com.fulltime.fullarm.infra.connection;

import java.net.Socket;

public class Connection {
    private static Socket socket;
    private static String host;
    private static Integer port;

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        Connection.socket = socket;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Connection.host = host;
    }

    public static Integer getPort() {
        return port;
    }

    public static void setPort(Integer port) {
        Connection.port = port;
    }
}
