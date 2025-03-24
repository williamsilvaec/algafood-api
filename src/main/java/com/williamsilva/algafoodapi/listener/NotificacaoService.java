package com.williamsilva.algafoodapi.listener;

import com.williamsilva.algafoodapi.di.modelo.Cliente;
import com.williamsilva.algafoodapi.di.notificacao.Notificador;
import com.williamsilva.algafoodapi.di.notificacao.TipoNotificador;
import com.williamsilva.algafoodapi.di.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @TipoNotificador
    @Autowired
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        Cliente cliente = event.getCliente();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }
}
