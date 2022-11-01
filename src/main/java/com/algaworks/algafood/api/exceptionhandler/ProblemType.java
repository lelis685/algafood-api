package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso nao encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/negocio-exception", "Violacao de regra de negocio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados invalidos");

    private String title;
    private String uri;

    ProblemType(String path, String title)  {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
