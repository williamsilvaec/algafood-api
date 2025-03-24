package com.williamsilva.algafoodapi.di.notificacao;

import com.williamsilva.algafoodapi.di.modelo.Cliente;

public interface Notificador {

    void notificar(Cliente cliente, String mensagem);
}
