package com.williamsilva.algafoodapi.api.controller;

import com.williamsilva.algafoodapi.api.model.CozinhaXmlWrapper;
import com.williamsilva.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.model.Cozinha;
import com.williamsilva.algafoodapi.domain.repository.CozinhaRepository;
import com.williamsilva.algafoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;
    private final CadastroCozinhaService cadastroCozinhaService;

    public CozinhaController(CozinhaRepository cozinhaRepository, CadastroCozinhaService cadastroCozinhaService) {
        this.cozinhaRepository = cozinhaRepository;
        this.cadastroCozinhaService = cadastroCozinhaService;
    }

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhaXmlWrapper listarXml() {
        List<Cozinha> cozinhas = cozinhaRepository.listar();
        return new CozinhaXmlWrapper(cozinhas);
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
        if (cozinha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.porId(cozinhaId);

        if (cozinhaAtual == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(cozinha, cozinhaAtual);

        cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
        return ResponseEntity.ok(cozinhaAtual);
    }

    @DeleteMapping("{cozinhaId}")
    public ResponseEntity<Void> remover(@PathVariable Long cozinhaId) {
        try {
            cadastroCozinhaService.remover(cozinhaId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
