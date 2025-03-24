package com.williamsilva.algafoodapi;

import com.williamsilva.algafoodapi.di.modelo.Cliente;
import com.williamsilva.algafoodapi.di.service.AtivacaoClienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeuPrimeiroController {

    private final AtivacaoClienteService atividacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService atividacaoClienteService) {
        this.atividacaoClienteService = atividacaoClienteService;
    }

    @GetMapping("/hello")
    public String hello() {
        Cliente joao = new Cliente("João", "joao@xyz.com", "3499998888");
        atividacaoClienteService.ativar(joao);

        return "hello";
    }
}
