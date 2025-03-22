package com.att.tdp.popcorn_palace.services;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import org.springframework.stereotype.Component;

@Component
public interface BookingService {
    BookingDto createBooking(BookingEntity bookingEntity);
}
