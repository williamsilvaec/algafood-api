package com.williamsilva.algafoodapi.domain.repository;

import com.williamsilva.algafoodapi.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    Restaurante porId(Long id);

    Restaurante salvar(Restaurante restaurante);

    List<Restaurante> listar();

    void remover(Long id);
}
