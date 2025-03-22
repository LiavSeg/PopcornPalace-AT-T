package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import com.att.tdp.popcorn_palace.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    private BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/bookings")
    public ResponseEntity<BookingDto> addBooking(@RequestBody BookingEntity bookingEntity) {
        BookingDto bookingDto = bookingService.createBooking(bookingEntity);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

}
