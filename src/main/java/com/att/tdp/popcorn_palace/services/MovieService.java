package com.att.tdp.popcorn_palace.services;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface MovieService {
    MovieEntity createMovie(MovieEntity movie);
    List<MovieEntity> findAll();
    void deleteMovie(String title);
    Optional<MovieEntity> findById(Integer id);
    interface BookingService {
        BookingDto createBooking();
    }
}
