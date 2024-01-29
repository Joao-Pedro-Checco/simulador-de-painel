package br.com.fulltime.fullarm.infra.connection.timeout;

public interface TimeoutHandler {
    void messageArrived();

    void initializeTimeout(long timeoutSeconds);
}
