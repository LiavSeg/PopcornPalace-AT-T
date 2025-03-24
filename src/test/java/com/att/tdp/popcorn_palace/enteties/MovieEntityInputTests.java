package com.att.tdp.popcorn_palace.enteties;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieEntityInputTests {

        private Validator validator;

        @BeforeEach
        void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        void testValidMovie() {
            MovieEntity movie = MovieEntity.builder()
                    .title("Test Movie")
                    .genre("Action")
                    .duration(120)
                    .rating(8.0)
                    .releaseYear(2020)
                    .build();

            Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testBlankTitle() {
            MovieEntity movie = MovieEntity.builder()
                    .title("")
                    .genre("Action")
                    .duration(120)
                    .rating(8.0)
                    .releaseYear(2020)
                    .build();

            Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
            assertEquals(1, violations.size());
            ConstraintViolation<MovieEntity> violation = violations.iterator().next();
            assertEquals("Movie title can't be blank", violation.getMessage());
        }

        @Test
        void testEmptyGenre() {
            MovieEntity movie = MovieEntity.builder()
                    .title("Test Movie")
                    .genre(null)
                    .duration(120)
                    .rating(8.0)
                    .releaseYear(2020)
                    .build();

            Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
            assertEquals(1, violations.size());
            ConstraintViolation<MovieEntity> violation = violations.iterator().next();
            assertEquals("Movie genre can't be empty", violation.getMessage());
        }

        @Test
        void testNegativeDuration() {
            MovieEntity movie = MovieEntity.builder()
                    .title("Test Movie")
                    .genre("Action")
                    .duration(-1)
                    .rating(8.0)
                    .releaseYear(2020)
                    .build();

            Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
            assertEquals(1, violations.size());
            ConstraintViolation<MovieEntity> violation = violations.iterator().next();
            assertEquals("Movie duration must be a positive integer", violation.getMessage());
        }

        @Test
        void testInvalidRating() {
            MovieEntity movie = MovieEntity.builder()
                    .title("Test Movie")
                    .genre("Action")
                    .duration(120)
                    .rating(-1.0)
                    .releaseYear(2020)
                    .build();

            Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
            assertEquals(1, violations.size());
            ConstraintViolation<MovieEntity> violation = violations.iterator().next();
            assertEquals("Movie rating's minimum value is 0", violation.getMessage());
        }

        @Test
        void testInvalidReleaseYear() {
            MovieEntity movie = MovieEntity.builder()
                    .title("Test Movie")
                    .genre("Action")
                    .duration(120)
                    .rating(8.0)
                    .releaseYear(1887)
                    .build();

            Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
            assertEquals(1, violations.size());
            ConstraintViolation<MovieEntity> violation = violations.iterator().next();
            assertEquals("Movie's release year can't be before 1888", violation.getMessage());
        }
    }


