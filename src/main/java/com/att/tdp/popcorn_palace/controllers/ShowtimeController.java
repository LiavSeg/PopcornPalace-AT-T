package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;
import com.att.tdp.popcorn_palace.domain.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.mappers.Mapper;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ShowtimeController {

    private ShowtimeService showtimeService;
    private Mapper<ShowtimeEntity, ShowtimeDto> showtimeMapper;
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeController.class);

    public ShowtimeController(ShowtimeService showtimeService,Mapper<ShowtimeEntity, ShowtimeDto> showtimeMapper){
        this.showtimeService = showtimeService;
        this.showtimeMapper = showtimeMapper;
    }

    @PostMapping(path = "/showtimes")
    public ShowtimeDto createShowtime(@RequestBody ShowtimeDto showtimeDto){
        ShowtimeEntity showtimeEntity = showtimeMapper.mapFrom(showtimeDto);
        ShowtimeEntity savedShowtimeEntity = showtimeService.createShowtime(showtimeEntity);
        return showtimeMapper.mapTo(savedShowtimeEntity);
    }

    @GetMapping(path = "/showtimes/{showtimeId}")
    public ShowtimeDto getShowtimeById(@PathVariable Integer showtimeId){
        ShowtimeEntity foundShowtime = showtimeService.findById(showtimeId);
        return showtimeMapper.mapTo(foundShowtime);
    }

    @PutMapping(path ="showtimes/update/{showtimeId}")
    public ResponseEntity<ShowtimeDto>  updateShowtime(@RequestBody ShowtimeDto showtimeDto,@PathVariable Integer showtimeId){

        ShowtimeDto showtimeToUpdate = getShowtimeById(showtimeId);
        if (showtimeToUpdate == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        showtimeDto.setId(showtimeId);
        createShowtime(showtimeDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(path ="showtimes/{showtimeId}")
    public ResponseEntity<ShowtimeDto>  updateShowtime(@PathVariable Integer showtimeId){
        try {
            ShowtimeDto showtimeToDelete = getShowtimeById(showtimeId);
            showtimeService.deleteShowtime(showtimeId);
            logger.info("Successfully deleted showtime with ID {}", showtimeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException | EntityNotFoundException e) {
            logger.warn("Showtime with ID {} not found, could not delete.", showtimeId);
            return ResponseEntity.notFound().build();
        }

    }
}
