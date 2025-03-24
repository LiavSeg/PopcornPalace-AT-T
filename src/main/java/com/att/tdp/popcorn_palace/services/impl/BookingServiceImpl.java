package com.att.tdp.popcorn_palace.services.impl;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import com.att.tdp.popcorn_palace.repositories.BookingRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.services.BookingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService  {

    private BookingRepository bookingRepository;
    private ShowtimeRepository showtimeRepository;
    public BookingServiceImpl(BookingRepository bookingRepository,ShowtimeRepository showtimeRepository) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public BookingDto createBooking(BookingEntity bookingEntity) {
        Optional<ShowtimeEntity> showtimeEntity = showtimeRepository.findById(bookingEntity.getShowtimeId());

        if (showtimeEntity.isEmpty())
            throw new IllegalStateException("Could not find showtime to book");
        Iterable<BookingEntity> bookingEntities = bookingRepository.findAllBookingsByshowtimeId(showtimeEntity.get().getId());
        for (BookingEntity booking : bookingEntities) {
            if (booking.getSeatNumber() == bookingEntity.getSeatNumber())
                throw new IllegalStateException(String.format(
                        "The requested seat (%d) conflicts with an existing booking in the showtime. " +
                                "Please choose a different seat or another showtime to avoid overlap.",
                        bookingEntity.getSeatNumber()
                ));
        }
        BookingEntity bookingEntityA = bookingRepository.save(bookingEntity);
        return new BookingDto(bookingEntityA.getBookingId().toString());
    }

}
