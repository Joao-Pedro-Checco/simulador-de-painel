package br.com.fulltime.fullarm.app;

import org.springframework.stereotype.Component;

@Component
public class ConnectionFieldsValidator {
    public boolean isValid(String host, String port, String account, String password, String macAddress) {
        return hostIsValid(host) && portIsValid(port) && accountIsValid(account)
                && passwordIsValid(password) && macAddressIsValid(macAddress);
    }

    private boolean hostIsValid(String host) {
        return !host.isEmpty();
    }

    private boolean portIsValid(String port) {
        return !port.isEmpty();
    }

    private boolean accountIsValid(String account) {
        if (account.isEmpty()) {
            return false;
        }

        return account.length() == 4;
    }

    private boolean passwordIsValid(String password) {
        if (password.isEmpty()) {
            return false;
        }

        return !(password.length() < 4 || password.length() > 6);
    }

    private boolean macAddressIsValid(String macAddress) {
        if (macAddress.isEmpty()) {
            return false;
        }

        return !(macAddress.length() < 6 || macAddress.length() > 12);
    }
}
