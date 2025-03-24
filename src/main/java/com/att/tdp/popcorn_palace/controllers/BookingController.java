package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import com.att.tdp.popcorn_palace.errors.ErrorResponse;
import com.att.tdp.popcorn_palace.services.BookingService;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class BookingController {
    private BookingService bookingService;
    private ShowtimeService showtimeService;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingService bookingService,ShowtimeService showtimeService) {
        this.bookingService = bookingService;
        this.showtimeService = showtimeService;
    }

    @PostMapping(path = "/bookings")
    public ResponseEntity<?> addBooking(@RequestBody BookingEntity bookingEntity) {
        try{

            ShowtimeEntity showtime = showtimeService.findById(bookingEntity.getShowtimeId());
            BookingDto bookingDto = bookingService.createBooking(bookingEntity);
            return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }
        catch(EntityNotFoundException e){

            logger.error("While booking a ticket {} ",e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(),HttpStatus.NOT_FOUND.value(),"NOT FOUND","/bookings",e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            logger.error("Conflict detected: Response Code: 409. Details: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(),HttpStatus.CONFLICT.value(),"CONFLICT","/bookings",e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);}
    }

}
