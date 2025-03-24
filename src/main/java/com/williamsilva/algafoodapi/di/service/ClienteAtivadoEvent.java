package com.williamsilva.algafoodapi.di.service;

import com.williamsilva.algafoodapi.di.modelo.Cliente;

public class ClienteAtivadoEvent {

    private final Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
