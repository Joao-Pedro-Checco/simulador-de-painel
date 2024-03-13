package br.com.fulltime.fullarm.app;

import org.springframework.stereotype.Component;

@Component
public class UserInputValidator {
    public boolean isValid(String host, String port, String account, String password, String macAddress) {
        return hostIsValid(host) && portIsValid(port)
                && accountIsValid(account) && passwordIsValid(password) && macAdressIsValid(macAddress);
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

    private boolean passwordIsValid(String password) {
        if (password.length() < 4 || password.length() > 6) {
            return false;
        }

        return !password.isEmpty();
    }

    private boolean macAdressIsValid(String macAddress) {
        return !macAddress.isEmpty() && macAddress.length() % 2 == 0;
    }
}
