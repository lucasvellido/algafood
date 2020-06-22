package com.algaworks.algafood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "produtos")
public class Produto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @CreationTimestamp
//    @Column(nullable = false)
//    private LocalDateTime dataCadastro;
//
//    @JsonIgnore
//    @UpdateTimestamp
//    @Column(nullable = false)
//    private LocalDateTime dataAtualizacao;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

    @ManyToOne
    private Restaurante restaurante;
}
