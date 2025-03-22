package com.att.tdp.popcorn_palace.services.impl;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import com.att.tdp.popcorn_palace.repositories.BookingRepository;
import com.att.tdp.popcorn_palace.services.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingDto createBooking(BookingEntity bookingEntity) {
        BookingEntity bookingEntityA = bookingRepository.save(bookingEntity);
        return new BookingDto(bookingEntityA.getBookingId().toString());
    }
}
