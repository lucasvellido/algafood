package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

//    List<Cozinha> listar();
//    List<Cozinha> consultarPorNome(String nome);
//    Cozinha buscar(Long id);
//    Cozinha salvar(Cozinha cozinha);
//    void remover(Long id);
}
