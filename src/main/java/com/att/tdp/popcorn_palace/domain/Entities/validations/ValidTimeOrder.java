package com.att.tdp.popcorn_palace.domain.Entities.validations;

import com.att.tdp.popcorn_palace.domain.Entities.validations.impl.ValidTimeOrderImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// The annotation to be used on the entity
@Constraint(validatedBy = ValidTimeOrderImpl.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeOrder  {
    String message() default "Start time must be before end time.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
