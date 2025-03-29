package com.williamsilva.algafoodapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.model.Restaurante;
import com.williamsilva.algafoodapi.domain.repository.RestauranteRepository;
import com.williamsilva.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);

        if (restaurante.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(restaurante.get());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

            if (restauranteAtual.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            BeanUtils.copyProperties(restaurante, restauranteAtual.get(),
                    "id", "formasPagamento", "endereco", "dataCadastro");

            Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteAtual.get());
            return ResponseEntity.ok(restauranteSalvo);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("{restauranteId}")
    public ResponseEntity<?> atualizarParcialmente(@PathVariable Long restauranteId,
                                                   @RequestBody Map<String, Object> campos) {

        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        if (restauranteAtual.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual.get());

        return atualizar(restauranteId, restauranteAtual.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}











