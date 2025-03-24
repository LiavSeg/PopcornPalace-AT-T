package com.att.tdp.popcorn_palace.enteties;
import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class BookingEntityInputsTests {
        private Validator validator;
        @BeforeEach
        public void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        public void testValidBooking() {
            BookingEntity booking = BookingEntity.builder()
                    .showtimeId(1)
                    .seatNumber(1)
                    .userId("user123")
                    .build();

            Set<ConstraintViolation<BookingEntity>> violations = validator.validate(booking);
            assertTrue(violations.isEmpty());
        }

        @Test
        public void testNullShowtimeId() {
            BookingEntity booking = BookingEntity.builder()
                    .seatNumber(1)
                    .userId("user123")
                    .build();

            Set<ConstraintViolation<BookingEntity>> violations = validator.validate(booking);
            assertEquals(1, violations.size());
            ConstraintViolation<BookingEntity> violation = violations.iterator().next();
            assertEquals("Showtime ID cannot be null", violation.getMessage());
        }

        @Test
        public void testNegativeSeatNumber() {
            BookingEntity booking = BookingEntity.builder()
                    .showtimeId(1)
                    .seatNumber(-1)
                    .userId("user123")
                    .build();

            Set<ConstraintViolation<BookingEntity>> violations = validator.validate(booking);
            assertEquals(1, violations.size());
            ConstraintViolation<BookingEntity> violation = violations.iterator().next();
            assertEquals("Seat number must be a positive integer", violation.getMessage());
        }

        @Test
        public void testNullUserId() {
            BookingEntity booking = BookingEntity.builder()
                    .showtimeId(1)
                    .seatNumber(1)
                    .build();

            Set<ConstraintViolation<BookingEntity>> violations = validator.validate(booking);
            assertEquals(1, violations.size());
            ConstraintViolation<BookingEntity> violation = violations.iterator().next();
            assertEquals("User ID cannot be null", violation.getMessage());
        }

    @Test
    public void tesInvalidUUID() {
        BookingEntity booking = BookingEntity.builder()
                .showtimeId(1)
                .seatNumber(1)
                .userId("123e4567-e89b-12d3-a456-42665544000023e4567-e89b-12d3-a456-426655440000")
                .build();

        Set<ConstraintViolation<BookingEntity>> violations = validator.validate(booking);
        assertEquals(1, violations.size());
        ConstraintViolation<BookingEntity> violation = violations.iterator().next();
        assertEquals("UUID string must not exceed 36 characters", violation.getMessage());
    }
}
