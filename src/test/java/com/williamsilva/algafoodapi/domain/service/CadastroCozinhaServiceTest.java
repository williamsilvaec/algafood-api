package com.williamsilva.algafoodapi.domain.service;

import com.williamsilva.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.model.Cozinha;
import com.williamsilva.algafoodapi.domain.repository.CozinhaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastroCozinhaServiceTest {

    @Mock
    private CozinhaRepository cozinhaRepository;

    @InjectMocks
    private CadastroCozinhaService cadastroCozinhaService;

    @Test
    void deveSalvarCozinhaComSucesso() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Italiana");

        when(cozinhaRepository.save(any(Cozinha.class))).thenReturn(cozinha);

        Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinha);

        assertNotNull(cozinhaSalva);
        assertEquals("Italiana", cozinhaSalva.getNome());
        verify(cozinhaRepository, times(1)).save(cozinha);
    }

    @Test
    void deveLancarExcecaoAoExcluirCozinhaInexistente() {
        Long cozinhaId = 2L;

        doThrow(EmptyResultDataAccessException.class).when(cozinhaRepository).deleteById(cozinhaId);

        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroCozinhaService.excluir(cozinhaId)
        );

        assertEquals("Cozinha de código 2 inexistente", exception.getMessage());
        verify(cozinhaRepository, times(1)).deleteById(cozinhaId);
    }

    @Test
    void deveLancarExcecaoAoExcluirCozinhaEmUso() {
        Long cozinhaId = 1L;

        doThrow(DataIntegrityViolationException.class).when(cozinhaRepository).deleteById(cozinhaId);

        EntidadeEmUsoException exception = assertThrows(
                EntidadeEmUsoException.class,
                () -> cadastroCozinhaService.excluir(cozinhaId)
        );

        assertEquals("A cozinha de código 1 não pode ser excluída, pois está em uso", exception.getMessage());
        verify(cozinhaRepository, times(1)).deleteById(cozinhaId);
    }
}