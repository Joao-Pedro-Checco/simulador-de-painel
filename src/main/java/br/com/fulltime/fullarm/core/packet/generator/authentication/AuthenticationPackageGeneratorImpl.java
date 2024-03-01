package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPackageGeneratorImpl implements AuthenticationPackageGenerator {
    @Override
    public AuthenticationPackage generatePackage(String connectionType, String account, String macAddress) {
        AuthenticationPackage authenticationPackage = new AuthenticationPackage();
        authenticationPackage.setConnectionType(connectionType);
        authenticationPackage.setAccount(account);
        authenticationPackage.setMacAddress(macAddress);

        return authenticationPackage;
    }
}
