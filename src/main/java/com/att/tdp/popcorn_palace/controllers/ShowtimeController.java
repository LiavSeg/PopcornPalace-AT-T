package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.errors.ErrorHandler;
import com.att.tdp.popcorn_palace.errors.ErrorResponse;
import com.att.tdp.popcorn_palace.mappers.Mapper;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ShowtimeController {

    private MovieService movieService;
    private ShowtimeService showtimeService;
    private Mapper<ShowtimeEntity, ShowtimeDto> showtimeMapper;
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeController.class);
    private ErrorHandler errorHandler;

    public ShowtimeController(ShowtimeService showtimeService,Mapper<ShowtimeEntity, ShowtimeDto> showtimeMapper,MovieService movieService) {
        this.errorHandler = new ErrorHandler(ShowtimeController.class);
        this.showtimeService = showtimeService;
        this.showtimeMapper = showtimeMapper;
        this.movieService = movieService;

    }

    @PostMapping(path = "/showtimes")
    public ResponseEntity<?> createShowtime(@RequestBody ShowtimeDto showtimeDto) {
        try {
            Integer id = showtimeDto.getMovieId();
            Optional<MovieEntity> movieEntity = movieService.findById(id);

            if (movieEntity.isEmpty()) {
                Exception e = new Exception(String.format("Failed to locate a movie with ID %d for the requested showtime. " +
                        "\nPlease ensure that the movie ID exists and try again.", id));
                return errorHandler.notFound("",e,"showtimes");
            }

            ShowtimeEntity showtimeEntity = showtimeMapper.mapFrom(showtimeDto);
            showtimeEntity.setMovie(movieEntity.get());
            ShowtimeEntity savedShowtimeEntity = showtimeService.createShowtime(showtimeEntity);
            return new ResponseEntity<>(showtimeMapper.mapTo(savedShowtimeEntity),HttpStatus.OK);
        }
        catch (IllegalStateException e){
            return errorHandler.conflict("",e,"showtimes");
        }
        catch (Exception e) {
            return errorHandler.badRequest("",e,"showtimes");
        }
    }

    @GetMapping(path = "/showtimes/{showtimeId}")
    public ResponseEntity<?> getShowtimeById(@PathVariable Integer showtimeId){
        try {
            ShowtimeEntity foundShowtime = showtimeService.findById(showtimeId);
            logger.info("Showtime with ID {} retrieved successfully", showtimeId);
            return new ResponseEntity<>(showtimeMapper.mapTo(foundShowtime),HttpStatus.OK);
        }

        catch (NoSuchElementException | EntityNotFoundException e) {
                return errorHandler.notFound(showtimeId,e,"showtimes");
            }
        catch (Exception e) {
            return errorHandler.badRequest("",e,"showtimes");
        }
    }

    @PostMapping(path ="showtimes/update/{showtimeId}")
    public ResponseEntity<?>  updateShowtime(@RequestBody ShowtimeDto showtimeDto,@PathVariable Integer showtimeId){
        try {
            ResponseEntity<?> showtimeToUpdate = getShowtimeById(showtimeId);
            if (showtimeToUpdate.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new EntityNotFoundException("Could not find a showtime with Id "+showtimeId);
            }
            showtimeDto.setMovieId(((ShowtimeDto)showtimeToUpdate.getBody()).getMovieId());
            showtimeDto.setId(showtimeId);
            createShowtime(showtimeDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException | EntityNotFoundException e) {
            return errorHandler.notFound(showtimeId,e,"showtimes");
        }
        catch (Exception e) {
            return errorHandler.badRequest("",e,"showtimes");
        }
    }

    @DeleteMapping(path ="showtimes/{showtimeId}")
    public ResponseEntity<?>  deleteShowtime(@PathVariable Integer showtimeId){
        try {
            ResponseEntity<?> showtimeToDelete = getShowtimeById(showtimeId);
            if (showtimeToDelete.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new EntityNotFoundException("Could not find a showtime with id :"+showtimeId);

            showtimeService.deleteShowtime(showtimeId);
            logger.info("Successfully deleted showtime with ID {}", showtimeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException | EntityNotFoundException e) {
            return errorHandler.notFound(showtimeId,e,"showtimes");
        }
        catch (Exception e) {
            return errorHandler.badRequest("",e,"showtimes");
        }

    }
}
