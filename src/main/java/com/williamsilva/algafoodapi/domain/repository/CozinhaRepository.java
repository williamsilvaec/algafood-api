package com.williamsilva.algafoodapi.domain.repository;

import com.williamsilva.algafoodapi.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    Cozinha salvar(Cozinha cozinha);
    Cozinha porId(Long id);
    List<Cozinha> listar();
    void remover(Long id);
}
