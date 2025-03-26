package com.williamsilva.algafoodapi.api.controller;

import com.williamsilva.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.model.Estado;
import com.williamsilva.algafoodapi.domain.repository.EstadoRepository;
import com.williamsilva.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository estadoRepository;
    private final CadastroEstadoService cadastroEstadoService;

    public EstadoController(EstadoRepository estadoRepository, CadastroEstadoService cadastroEstadoService) {
        this.estadoRepository = estadoRepository;
        this.cadastroEstadoService = cadastroEstadoService;
    }

    @GetMapping
    public List<Estado> listaEstados() {
        return estadoRepository.listar();
    }

    @GetMapping("{estadoId}")
    public ResponseEntity<Estado> buscaEstado(@PathVariable Long estadoId) {
        Estado estado = estadoRepository.buscar(estadoId);

        if (estado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado cadastrar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("{estadoId}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        Estado estadoAtual = estadoRepository.buscar(estadoId);

        if (estadoAtual == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoAtual = cadastroEstadoService.salvar(estadoAtual);
        return ResponseEntity.ok(estadoAtual);
    }

    @DeleteMapping("{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
