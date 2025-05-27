package com.williamsilva.algafoodapi.api.exceptionhandler;

public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    RECURSO_EM_USO("/recurso-em-uso", "Recurso em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_SISTEMA("/erro-sistema", "Erro interno do sistema"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível");

    private final String titulo;
    private final String uri;

    ProblemType(String caminho, String titulo) {
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
