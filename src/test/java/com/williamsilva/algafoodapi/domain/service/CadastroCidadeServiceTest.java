package com.williamsilva.algafoodapi.domain.service;

import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.model.Cidade;
import com.williamsilva.algafoodapi.domain.model.Estado;
import com.williamsilva.algafoodapi.domain.repository.CidadeRepository;
import com.williamsilva.algafoodapi.domain.repository.EstadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastroCidadeServiceTest {


    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private CadastroCidadeService cadastroCidadeService;

    @Test
    void deveSalvarCidadeComSucesso() {
        Estado estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        Cidade cidade = new Cidade();
        cidade.setNome("Campinas");
        cidade.setEstado(estado);

        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estado));
        when(cidadeRepository.save(cidade)).thenReturn(cidade);

        Cidade cidadeSalva = cadastroCidadeService.salvar(cidade);

        assertNotNull(cidadeSalva);
        assertEquals("Campinas", cidadeSalva.getNome());
        assertEquals("São Paulo", cidadeSalva.getEstado().getNome());

    }

    @Test
    void deveLancarExcecaoQuandoEstadoNaoForEncontrado() {
        Estado estado = new Estado();
        estado.setId(2L);

        Cidade cidade = new Cidade();
        cidade.setNome("Campinas");
        cidade.setEstado(estado);

        when(estadoRepository.findById(2L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroCidadeService.salvar(cidade)
        );

        assertEquals("Não existe cadastro de estado com código 2", exception.getMessage());
    }

  
}