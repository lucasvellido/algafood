package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CadastroCozinhaService {

    public static final String COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com o id %d";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);

        } catch (EmptyResultDataAccessException e) {
            /*Duas maneiras de lançar a exception*/
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(COZINHA_NAO_ENCONTRADA, cozinhaId));
//            throw new EntidadeNaoEncontradaException(
//                    String.format("Não existe um cadastro de cozinha com o id %d", cozinhaId)
//            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de id %d não pode ser removida, pois está em uso", cozinhaId));
        }
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(COZINHA_NAO_ENCONTRADA));
    }
}
