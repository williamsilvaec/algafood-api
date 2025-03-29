package com.williamsilva.algafoodapi.api.controller;

import com.williamsilva.algafoodapi.domain.model.Restaurante;
import com.williamsilva.algafoodapi.domain.repository.RestauranteRepository;
import com.williamsilva.algafoodapi.infrastructure.spec.RestauranteComFreteGratisSpec;
import com.williamsilva.algafoodapi.infrastructure.spec.RestauranteComNomeSemelhanteSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public List<Restaurante> pesquisarPorNome(String nome, Long cozinhaId) {
        return restauranteRepository.consultaPorNome(nome, cozinhaId);
    }

    @GetMapping("/filtro")
    public List<Restaurante> filtrar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.filtrarPor(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/por-nome")
    public List<Restaurante> porNome(String nome) {
        var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);
        var comFreteGratis = new RestauranteComFreteGratisSpec();

        return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));
    }
}
