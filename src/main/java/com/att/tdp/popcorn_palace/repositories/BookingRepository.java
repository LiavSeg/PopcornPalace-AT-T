package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, BookingDto> {
    Iterable<BookingEntity> findAllBookingsByshowtimeId(Integer showtime_id);
    void deleteAllByShowtimeId(Integer showtimeId);
}
