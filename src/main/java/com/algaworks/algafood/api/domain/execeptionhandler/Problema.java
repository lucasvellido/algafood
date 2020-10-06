package com.algaworks.algafood.api.domain.execeptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)// não vai retornar os atributos nulos
@Getter
@Builder
public class Problema {
    private Integer status;
    private String type;
    private String title;
    private String detail;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }
}
