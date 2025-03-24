package com.att.tdp.popcorn_palace.enteties;

import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.ShowtimeDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ShowtimeEntityInputTests {
    private ShowtimeEntity underTest;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidShowtime() {
        MovieEntity movie = MovieEntity.builder()
                .title("Test Movie")
                .genre("Action")
                .duration(120)
                .rating(8.0)
                .releaseYear(2020)
                .build();

        ShowtimeEntity showtime = ShowtimeEntity.builder()
                .movie(movie)
                .theater("Cinema City")
                .price(30.2)
                .startTime(OffsetDateTime.now().toString())
                .endTime(OffsetDateTime.now().plusHours(2).toString())
                .build();

        Set<ConstraintViolation<ShowtimeEntity>> violations = validator.validate(showtime);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testMissingStartTime() {
        MovieEntity movie = MovieEntity.builder()
                .title("Test Movie")
                .genre("Action")
                .duration(120)
                .rating(8.0)
                .releaseYear(2020)
                .build();

        ShowtimeEntity showtime = ShowtimeEntity.builder()
                .movie(movie)
                .theater("Cinema City")
                .price(30.2)
                .endTime(OffsetDateTime.now().plusHours(2).toString())
                .build();

        Set<ConstraintViolation<ShowtimeEntity>> violations = validator.validate(showtime);
        assertEquals(1, violations.size());
        ConstraintViolation<ShowtimeEntity> violation = violations.iterator().next();
        assertEquals("Showtime must have a start time", violation.getMessage());
    }

    @Test
    void testMissingEndTime() {
        MovieEntity movie = MovieEntity.builder()
                .title("Test Movie")
                .genre("Action")
                .duration(120)
                .rating(8.0)
                .releaseYear(2020)
                .build();

        ShowtimeEntity showtime = ShowtimeEntity.builder()
                .movie(movie)
                .theater("Cinema City")
                .price(30.2)
                .startTime(OffsetDateTime.now().toString())
                .build();

        Set<ConstraintViolation<ShowtimeEntity>> violations = validator.validate(showtime);
        assertEquals(1, violations.size());
        ConstraintViolation<ShowtimeEntity> violation = violations.iterator().next();
        assertEquals("Showtime must have an end time", violation.getMessage());
    }

    @Test
    void testInvalidStartEndTime() {
        MovieEntity movie = MovieEntity.builder()
                .title("Test Movie")
                .genre("Action")
                .duration(120)
                .rating(8.0)
                .releaseYear(2020)
                .build();

        ShowtimeEntity showtime = ShowtimeEntity.builder()
                .movie(movie)
                .theater("Cinema City")
                .price(30.2)
                .startTime(OffsetDateTime.now().plusHours(2).toString())
                .endTime(OffsetDateTime.now().toString())
                .build();

        Set<ConstraintViolation<ShowtimeEntity>> violations = validator.validate(showtime);
        ConstraintViolation<ShowtimeEntity> violation = violations.iterator().next();
        assertEquals(1, violations.size());
        assertEquals("Showtime starting time must be before ending time.", violation.getMessage());
    }

    @Test
    void testEndTimeIsoInvalid() {
        MovieEntity movie = MovieEntity.builder()
                .title("Test Movie")
                .genre("Action")
                .duration(120)
                .rating(8.0)
                .releaseYear(2020)
                .build();

        ShowtimeEntity showtime = ShowtimeEntity.builder()
                .movie(movie)
                .theater("Cinema City")
                .price(30.2)
                .startTime("invalid")
                .endTime(OffsetDateTime.now().toString())
                .build();
        Set<ConstraintViolation<ShowtimeEntity>> violations = validator.validate(showtime);
        ConstraintViolation<ShowtimeEntity> violation = violations.iterator().next();
        assertEquals("Invalid time format. Please use ISO offset date time format.", violation.getMessage());

    }
}
