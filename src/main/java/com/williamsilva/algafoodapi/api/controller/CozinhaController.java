package com.williamsilva.algafoodapi.api.controller;

import com.williamsilva.algafoodapi.api.model.CozinhaXmlWrapper;
import com.williamsilva.algafoodapi.domain.model.Cozinha;
import com.williamsilva.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaController(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
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
    public Cozinha buscar(@PathVariable Long cozinhaId) {
        return cozinhaRepository.porId(cozinhaId);
    }
}
