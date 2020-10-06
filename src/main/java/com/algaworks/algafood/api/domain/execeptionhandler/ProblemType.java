package com.algaworks.algafood.api.domain.execeptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    DADOS_INVALIDOS("dados-invalidos", "Dados inválidos"),
    MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível"),
    ENTIDADE_NAO_ENCONTRADA("endidade-nao-encontrada", "Entidade não encontrada");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br/" + path;
        this.title = title;
    }
}
