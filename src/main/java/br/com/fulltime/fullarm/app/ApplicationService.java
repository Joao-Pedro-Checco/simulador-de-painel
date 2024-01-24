package br.com.fulltime.fullarm.app;

import br.com.fulltime.fullarm.infra.connection.ConnectionHandler;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    private final ConnectionHandler connectionHandler;

    public ApplicationService(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void start() {
        String host = System.getenv("HOST");
        Integer port = Integer.valueOf(System.getenv("PORT"));

        connectionHandler.createConnection(host, port);
        connectionHandler.listenForMessage();
    }
}
