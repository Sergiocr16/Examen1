package com.cenfotec.examen1.constrains;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by melvin on 2/18/2017.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = HorarioValidator.class)
@Documented
public @interface HorarioTimeConstrain {
    String message() default "{com.cenfotec.examen1.constrains.HorarioDTOTimeConstrain}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
