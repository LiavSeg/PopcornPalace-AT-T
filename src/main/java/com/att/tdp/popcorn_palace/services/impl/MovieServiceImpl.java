package com.att.tdp.popcorn_palace.services.impl;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieServiceImpl implements MovieService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final ShowtimeService showtimeService;
    private final Validator validator;

    public MovieServiceImpl(MovieRepository movieRepository, Validator validator,ShowtimeRepository showtimeRepository,ShowtimeService showtimeService) {
        this.movieRepository = movieRepository;
        this.validator = validator;
        this.showtimeRepository =  showtimeRepository;
        this.showtimeService =  showtimeService;
    }
    @Override
    public MovieEntity createMovie(MovieEntity movieEntity){
        validateMovie(movieEntity);
        return movieRepository.save(movieEntity);
    }


    @Override
    public List<MovieEntity> findAll(){
        return StreamSupport.stream(movieRepository
                        .findAll().spliterator(), false
                ).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public void deleteMovie(String title){
        Iterable<MovieEntity> moviesToDelete = movieRepository.findByTitle(title);
        if (!moviesToDelete.iterator().hasNext()) {
            throw new NoSuchElementException(String.format("Movie with a %s was not found - can't delete", title));
        }
        for (MovieEntity movieEntity : moviesToDelete) {
            Iterable<ShowtimeEntity> showtimesWithThisMovie = showtimeRepository.findByMovieId(movieEntity.getId());
            for (ShowtimeEntity showtime : showtimesWithThisMovie) {
                showtimeService.deleteShowtime(showtime.getId());
            }
            movieRepository.deleteById(movieEntity.getId());
        }
    }

    @Override
    public Optional<MovieEntity> findById(Integer id) {
        return movieRepository.findById(id);
    }

    public void validateMovie(MovieEntity movieEntity) {
        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movieEntity);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException("Validation failed: " + errorMessages);
        }
    }

}
