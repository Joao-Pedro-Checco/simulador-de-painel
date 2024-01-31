package br.com.fulltime.fullarm.core.connection.authenticator;

import br.com.fulltime.fullarm.core.connection.timeout.TimeoutHandler;
import br.com.fulltime.fullarm.infra.connection.sender.PackageSender;
import org.springframework.stereotype.Service;

@Service
public class PanelAuthenticatorImpl implements PanelAuthenticator {
    private final PackageSender packageSender;
    private final TimeoutHandler timeoutHandler;

    public PanelAuthenticatorImpl(PackageSender packageSender, TimeoutHandler timeoutHandler) {
        this.packageSender = packageSender;
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public void authenticatePanel(byte[] authenticationPackage) {
        System.out.println("================{Enviando pacote de autenticação}================");
        packageSender.sendPackage(authenticationPackage);

        System.out.println("================{Aguardando resposta do servidor}================");
        timeoutHandler.initializeTimeout(30);
    }
}
