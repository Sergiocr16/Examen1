package com.cenfotec.examen1.constrains;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
/**
 * Created by melvin on 1/31/2017.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = HorarioDTOValidator.class)
@Documented
public @interface HorarioDTOTimeConstrain {

    String message() default "{com.cenfotec.examen1.constrains.HorarioDTOTimeConstrain}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
