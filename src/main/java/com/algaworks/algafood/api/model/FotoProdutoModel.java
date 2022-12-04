package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Produto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class FotoProdutoModel {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}  