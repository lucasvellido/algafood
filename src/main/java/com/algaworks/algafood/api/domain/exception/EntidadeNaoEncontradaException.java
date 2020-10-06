package com.algaworks.algafood.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


@ResponseStatus(value = HttpStatus.NOT_FOUND/*, *reason = "minha mensagem"*/)
public class EntidadeNaoEncontradaException extends RuntimeException {
//public class EntidadeNaoEncontradaException extends ResponseStatusException {

    public EntidadeNaoEncontradaException(String msg) {
        super(msg);
    }

    public EntidadeNaoEncontradaException(String msg, Throwable causa) {
        super(msg, causa);
    }

//    public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
//        super(status, reason);
//    }
//
//    public EntidadeNaoEncontradaException(String reason) {
//        super(HttpStatus.NOT_FOUND, reason);
//    }

}

