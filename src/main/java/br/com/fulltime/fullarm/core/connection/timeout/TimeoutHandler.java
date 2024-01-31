package br.com.fulltime.fullarm.core.connection.timeout;

public interface TimeoutHandler {
    void messageArrived();

    boolean initializeTimeout(long timeoutSeconds);
}
