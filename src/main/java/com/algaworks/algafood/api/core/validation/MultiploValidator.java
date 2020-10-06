package com.algaworks.algafood.api.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        if(number != null) {
            BigDecimal valorDecimal = BigDecimal.valueOf(number.doubleValue());
            BigDecimal multiploDecimal = BigDecimal.valueOf(numeroMultiplo);
            BigDecimal resto = valorDecimal.remainder(multiploDecimal); // retorna o resto da divisão

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }
}
