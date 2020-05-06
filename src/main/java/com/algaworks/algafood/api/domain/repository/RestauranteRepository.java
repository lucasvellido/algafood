package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository
        extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> consultaPorNome(String nome, @Param("id") Long cozinha);

    /*Ele busca no arquivo RestauranteRepositoryImpl (por isso no nome tem que ter o sufixo Impl)*/
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

//    List<Restaurante> listar();
//    Restaurante buscar(Long id);
//    Restaurante salvar(Restaurante restaurante);
//    void remover(Long id);
}
