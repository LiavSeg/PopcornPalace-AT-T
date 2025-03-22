package com.att.tdp.popcorn_palace.controllers;


import com.att.tdp.popcorn_palace.domain.dto.MovieDto;
import com.att.tdp.popcorn_palace.mappers.Mapper;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {


    private MovieService movieService;
    private Mapper<MovieEntity, MovieDto> movieMapper;

    public MovieController(MovieService movieService, Mapper<MovieEntity,MovieDto> movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping(path = "/movies")
    public MovieDto createMovie(@RequestBody MovieDto movie) {
        MovieEntity movieEntity = movieMapper.mapFrom(movie);
        MovieEntity savedMovieEntity = movieService.createMovie(movieEntity);
        return movieMapper.mapTo(savedMovieEntity);
    }

    @GetMapping(path = "/movies")
    public List<MovieDto> getAllMovies() {
        List<MovieEntity> movies = movieService.findAll();
        return movies.stream()
                .map(movieMapper::mapTo)
                .collect(Collectors.toList());
    }


    @PutMapping(path = "/movies/update/{title}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable String title, @RequestBody MovieDto movieDto) {
        List<MovieDto> allMovies = getAllMovies();

        for (MovieDto movieDto1 : allMovies) {
            if (movieDto1.getTitle().equals(title)) {
                movieDto.setId(movieDto1.getId());
                MovieEntity movieEntity = movieMapper.mapFrom(movieDto);
                movieService.createMovie(movieEntity);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/movies/{title}")
    public ResponseEntity deleteMovie(@PathVariable("title") String title ) {
        movieService.deleteMovie(title);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

