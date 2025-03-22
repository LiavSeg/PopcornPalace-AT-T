package com.att.tdp.popcorn_palace.services.impl;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.services.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;

    }
    @Override
    public MovieEntity createMovie(MovieEntity movieEntity) {
        return movieRepository.save(movieEntity);
    }


    @Override
    public List<MovieEntity> findAll(){
        return StreamSupport.stream(movieRepository
                        .findAll().spliterator(), false
                ).collect(Collectors.toList());

    }

    @Override
    public void deleteMovie(String title){
        Iterable<MovieEntity> moviesToDelete = movieRepository.findByTitle(title);
        for (MovieEntity movieEntity : moviesToDelete) {
            movieRepository.deleteById(movieEntity.getId());
            return;
        }
    }

}
