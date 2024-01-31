package br.com.fulltime.fullarm.app;

import org.springframework.stereotype.Component;

@Component
public class UserInputValidator {
    public boolean isValid(String host, String port, String account, String macAddress) {
        return hostIsValid(host) && portIsValid(port) && accountIsValid(account) && macAdressIsValid(macAddress);
    }

    private boolean hostIsValid(String host) {
        return !host.isEmpty();
    }

    private boolean portIsValid(String port) {
        return !port.isEmpty();
    }

    private boolean accountIsValid(String account) {
        return !account.isEmpty();
    }

    private boolean macAdressIsValid(String macAddress) {
        return !macAddress.isEmpty() && macAddress.length() % 2 == 0;
    }
}
