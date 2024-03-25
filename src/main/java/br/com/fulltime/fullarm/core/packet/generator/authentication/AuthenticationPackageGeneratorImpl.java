package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;
import br.com.fulltime.fullarm.core.panel.Panel;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPackageGeneratorImpl implements AuthenticationPackageGenerator {
    @Override
    public AuthenticationPackage generatePackage() {
        AuthenticationPackage authenticationPackage = new AuthenticationPackage();
        authenticationPackage.setConnectionType(Panel.getConnectionType());
        authenticationPackage.setAccount(Panel.getAccount());
        authenticationPackage.setMacAddress(Panel.getMacAddress());

        return authenticationPackage;
    }
}
