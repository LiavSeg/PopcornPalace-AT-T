package com.att.tdp.popcorn_palace.services.impl;

import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private ShowtimeRepository showtimeRepository;
    private MovieRepository movieRepository;

    public ShowtimeServiceImpl(MovieRepository movieRepository, ShowtimeRepository showtimeRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public ShowtimeEntity createShowtime(ShowtimeEntity showtimeEntity) {
        return showtimeRepository.save(showtimeEntity);
    }

   @Override
    public ShowtimeEntity findById(Integer id) {
       return showtimeRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Showtime not found with ID: " + id));
    }

    @Override
    public void deleteShowtime(Integer id) {
        showtimeRepository.deleteById(id);
    }
}
