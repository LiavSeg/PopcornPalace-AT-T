package com.att.tdp.popcorn_palace.domain.Entities.validations.impl;

import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.Entities.validations.ValidStartEndTimes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidStartEndTimesImpl implements ConstraintValidator<ValidStartEndTimes, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        try {
            if (o == null) {
                return true;
            }

            String startTimeVal = ((ShowtimeEntity)o).getStartTime();
            String endTimeVal= ((ShowtimeEntity)o).getEndTime();
            if (startTimeVal == null || endTimeVal == null) {
                return true; // will be handled by NotNull annotation
            }

            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

            OffsetDateTime startTime = OffsetDateTime.parse((startTimeVal),formatter);
            OffsetDateTime endTime  = OffsetDateTime.parse(endTimeVal, formatter);
            if (!startTime.isBefore(endTime)) {
                String message = "Showtime starting time must be before ending time.";
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
            }
            return true;
        }
        catch (DateTimeParseException e){
            return false;
        }
        catch (Exception e) {
            Logger.getLogger(ValidStartEndTimes.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
