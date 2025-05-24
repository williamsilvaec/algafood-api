package com.williamsilva.algafoodapi.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(Long restauranteId) {
        super(String.format("Restaturante de código %d não encontrado", restauranteId));
    }
}
