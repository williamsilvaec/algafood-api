package com.williamsilva.algafoodapi.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

    protected EntidadeNaoEncontradaException(String message) {
        super(message);
    }


}
