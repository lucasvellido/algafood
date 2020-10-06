package com.algaworks.algafood.api.domain.execeptionhandler;

import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntidadeNaoEncontradaException.class})
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException
            (EntidadeNaoEncontradaException e, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        Problema problema = createProblemBuilder(httpStatus, problemType, detail).build();

//        Problema problema = Problema.builder()
//                .status(httpStatus.value())
//                .type("https://algafood.com.br/entidade-nap-encontrada")
//                .title("Endidade não encontrada")
//                .detail(e.getMessage())
//                .build();


        return handleExceptionInternal(e, problema, new HttpHeaders(), httpStatus , request);
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        Problema problema = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(e, problema, headers, status , request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

        String path = e.getPath().stream()
                .map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
        + "que é um tipo inválido. Corrija  e informe um valor compativél com o tipo %s.",
                path, e.getValue(), e.getTargetType().getSimpleName());

        Problema problema = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(e, problema, headers, status , request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente mais tarde.";

        BindingResult bindingResult = e.getBindingResult();

        List<Problema.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> Problema.Field.builder()
                        .name(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        Problema problema = createProblemBuilder(status, problemType, detail)
                .fields(problemFields)
                .build();

        return handleExceptionInternal(e, problema, headers, status , request);
    }

    /*alterando a respostas dos erros internos*/
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problema.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }


        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problema.ProblemaBuilder createProblemBuilder(HttpStatus status,
                                                          ProblemType problemType, String detail) {
        return Problema.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
