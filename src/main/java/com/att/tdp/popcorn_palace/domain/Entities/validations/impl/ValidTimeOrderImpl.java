package com.att.tdp.popcorn_palace.domain.Entities.validations.impl;


import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.Entities.validations.ValidTimeOrder;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidTimeOrderImpl implements ConstraintValidator<ValidTimeOrder, ShowtimeEntity> {


    @Override
    public boolean isValid(ShowtimeEntity showtimeEntity, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
