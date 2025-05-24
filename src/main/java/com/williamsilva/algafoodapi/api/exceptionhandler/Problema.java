package com.williamsilva.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {

    private final Integer status;
    private final String tipo;
    private final String titulo;
    private final String detalhe;

    public Problema(Builder builder) {
        this.status = builder.status;
        this.tipo = builder.tipo;
        this.titulo = builder.titulo;
        this.detalhe = builder.detalhe;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public static class Builder {
        private Integer status;
        private String tipo;
        private String titulo;
        private String detalhe;

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder detalhe(String detalhe) {
            this.detalhe = detalhe;
            return this;
        }

        public Problema build() {
            return new Problema(this);
        }
    }
}
