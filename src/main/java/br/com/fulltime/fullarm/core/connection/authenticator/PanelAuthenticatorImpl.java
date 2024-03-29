package br.com.fulltime.fullarm.core.connection.authenticator;

import br.com.fulltime.fullarm.core.connection.timeout.TimeoutHandler;
import br.com.fulltime.fullarm.core.logger.Logger;
import br.com.fulltime.fullarm.core.packet.authentication.AuthenticationPackage;
import br.com.fulltime.fullarm.infra.packet.PackageSender;
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
    public boolean authenticatePanel(AuthenticationPackage authenticationPackage) {
        Logger.log("Enviando pacote de autenticação");
        packageSender.sendPackage(authenticationPackage);

        Logger.log("Aguardando resposta do servidor");
        return timeoutHandler.initializeTimeout(30);
    }
}
