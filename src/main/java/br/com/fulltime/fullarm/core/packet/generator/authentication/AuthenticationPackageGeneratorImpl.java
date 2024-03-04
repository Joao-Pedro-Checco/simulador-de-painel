package br.com.fulltime.fullarm.core.packet.generator.authentication;

import br.com.fulltime.fullarm.core.packet.AuthenticationPackage;
import br.com.fulltime.fullarm.core.panel.ConnectionType;
import br.com.fulltime.fullarm.core.panel.Panel;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPackageGeneratorImpl implements AuthenticationPackageGenerator {
    @Override
    public AuthenticationPackage generatePackage(ConnectionType connectionType, String macAddress) {
        AuthenticationPackage authenticationPackage = new AuthenticationPackage();
        authenticationPackage.setConnectionType(connectionType);
        authenticationPackage.setAccount(Panel.getAccount());
        authenticationPackage.setMacAddress(macAddress);

        return authenticationPackage;
    }
}
