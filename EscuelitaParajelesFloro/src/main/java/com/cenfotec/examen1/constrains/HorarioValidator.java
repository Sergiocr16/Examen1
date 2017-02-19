package com.cenfotec.examen1.constrains;

import com.cenfotec.examen1.domain.Horario;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by melvin on 2/18/2017.
 */
public class HorarioValidator implements ConstraintValidator<HorarioTimeConstrain, Horario> {
    @Override
    public void initialize(final HorarioTimeConstrain constraintAnnotation) {}
    @Override
    public boolean isValid(final Horario value, final ConstraintValidatorContext context) {
        return value.getHoraInicio() < value.getHoraFin();
    }

}
