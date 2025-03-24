package com.att.tdp.popcorn_palace.controllers;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;
import com.att.tdp.popcorn_palace.errors.ErrorHandler;
import com.att.tdp.popcorn_palace.mappers.Mapper;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class MovieController {


    private final MovieService movieService;
    private final Mapper<MovieEntity, MovieDto> movieMapper;
    private final ErrorHandler errorHandler;
    private final String currPath = "/movies";
    public MovieController(MovieService movieService, Mapper<MovieEntity,MovieDto> movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.errorHandler = new ErrorHandler(MovieController.class);
    }

    @PostMapping(path = "/movies")
    public ResponseEntity<?> createMovie(@RequestBody MovieDto movie) {
        try{
            MovieEntity movieEntity = movieMapper.mapFrom(movie);
            MovieEntity savedMovieEntity = movieService.createMovie(movieEntity);
            return new ResponseEntity<>(movieMapper.mapTo(savedMovieEntity),HttpStatus.OK);
        }
        catch(Exception e){
            return errorHandler.badRequest("",e,currPath);
        }
    }


    @GetMapping(path = "/movies/all")
    public ResponseEntity<?> getAllMovies() {
        try{
        List<MovieEntity> movies = movieService.findAll();
        List<MovieDto> moviesDto = movies.stream().map(movieMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(moviesDto,HttpStatus.OK);
    }
        catch(Exception e){
            return errorHandler.badRequest("",e,currPath);
        }
    }


    @PostMapping(path = "/movies/update/{title}")
    public ResponseEntity<?> updateMovie(@PathVariable String title, @RequestBody MovieDto movieDto) {
        ResponseEntity<?> response = getAllMovies();  // Call the method
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST)
            return response;

        List<MovieDto> allMovies = (List<MovieDto>)response.getBody();
        try {
            for (MovieDto movieDto1 : allMovies) {
                if (movieDto1.getTitle().equals(title)) {
                    movieDto.setId(movieDto1.getId());
                    MovieEntity movieEntity = movieMapper.mapFrom(movieDto);
                    movieService.createMovie(movieEntity);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        catch(NoSuchElementException e){
            return errorHandler.notFound(title,e,currPath);
        }
        catch(Exception e){
            return errorHandler.badRequest("",e,currPath);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/movies/{title}")
    public ResponseEntity<?> deleteMovie(@PathVariable("title") String title ) {
        try {
            movieService.deleteMovie(title);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            return errorHandler.notFound(title,e,currPath);
        }
        catch(Exception e){
            return errorHandler.badRequest("",e,currPath);
        }
    }
}


