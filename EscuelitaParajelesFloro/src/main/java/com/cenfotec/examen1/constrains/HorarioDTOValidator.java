package com.cenfotec.examen1.constrains;

/**
 * Created by melvin on 1/31/2017.
 */



import com.cenfotec.examen1.service.dto.HorarioDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HorarioDTOValidator implements ConstraintValidator<HorarioDTOTimeConstrain, HorarioDTO>
{
    @Override
    public void initialize(final HorarioDTOTimeConstrain constraintAnnotation) {}
    @Override
    public boolean isValid(final HorarioDTO value, final ConstraintValidatorContext context)
    {
        return value.getHoraInicio() < value.getHoraFin();
    }
}
