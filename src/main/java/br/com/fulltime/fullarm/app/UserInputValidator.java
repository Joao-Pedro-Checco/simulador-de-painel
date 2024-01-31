package br.com.fulltime.fullarm.app;

import org.springframework.stereotype.Component;

@Component
public class UserInputValidator {
    public boolean isValid(String host, String port, String account, String macAddress) {
        return !host.isEmpty() && !port.isEmpty() && !account.isEmpty() && !macAddress.isEmpty();
    }
}
