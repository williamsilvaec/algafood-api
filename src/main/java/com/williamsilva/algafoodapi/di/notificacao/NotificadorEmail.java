package com.williamsilva.algafoodapi.di.notificacao;

import com.williamsilva.algafoodapi.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@TipoNotificador
@Component
public class NotificadorEmail implements Notificador {

//    @Value("${notificador.email.host-servidor}")
//    private String serverHost;
//
//    @Value("${notificador.email.porta-servidor}")
//    private int serverPort;

    @Autowired
    private NotificadorProperties properties;

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("Servidor: " + properties.getHostServidor() + ":" + properties.getPortaServidor());

        System.out.printf("Notificando %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
