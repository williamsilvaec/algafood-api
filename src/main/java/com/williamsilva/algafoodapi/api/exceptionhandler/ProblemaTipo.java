package com.williamsilva.algafoodapi.api.exceptionhandler;

public enum ProblemaTipo {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível");

    private final String titulo;
    private final String uri;

    ProblemaTipo(String caminho, String titulo) {
        this.uri = "https://algafood.com.br" + caminho;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUri() {
        return uri;
    }
}
