package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import com.att.tdp.popcorn_palace.errors.ErrorHandler;
import com.att.tdp.popcorn_palace.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class BookingController {
    private final BookingService bookingService;
    //private ShowtimeService showtimeService;
    private final ErrorHandler errorHandler;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
//        this.showtimeService = showtimeService;
        this.errorHandler = new ErrorHandler(BookingController.class);
    }

    @PostMapping(path = "/bookings")
    public ResponseEntity<?> addBooking(@RequestBody BookingEntity bookingEntity) {
        try {
            BookingDto bookingDto = bookingService.createBooking(bookingEntity);
            return new ResponseEntity<>(bookingDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return errorHandler.notFound("", e, "/bookings");
        } catch (IllegalStateException e) {
            return errorHandler.conflict("", e, "/bookings");
        } catch (Exception e) {
            return errorHandler.badRequest("", e, "/bookings");
        }
    }
}
