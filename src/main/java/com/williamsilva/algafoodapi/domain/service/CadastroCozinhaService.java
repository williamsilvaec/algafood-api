package com.williamsilva.algafoodapi.domain.service;

import com.williamsilva.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.model.Cozinha;
import com.williamsilva.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public void remover(Long cozinhaId) {
        try {
            cozinhaRepository.remover(cozinhaId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Cozinha de código %d inexistente", cozinhaId)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format("A cozinha de código %d não pode ser excluída, pois" +
                    " está em uso", cozinhaId));
        }
    }
}
