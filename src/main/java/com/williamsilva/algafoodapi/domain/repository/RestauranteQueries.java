package com.williamsilva.algafoodapi.domain.repository;

import com.williamsilva.algafoodapi.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteQueries {

    List<Restaurante> filtrarPor(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
