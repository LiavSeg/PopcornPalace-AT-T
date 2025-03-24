package com.att.tdp.popcorn_palace.services.impl;

import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.repositories.BookingRepository;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    //private final MovieRepository movieRepository;
    private final BookingRepository bookingRepository;

    public ShowtimeServiceImpl(MovieRepository movieRepository, ShowtimeRepository showtimeRepository, BookingRepository bookingRepository) {
        //this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public ShowtimeEntity createShowtime(ShowtimeEntity showtimeEntity) {

        Iterable<ShowtimeEntity> ShowtimeEntityInCinema = showtimeRepository.findByTheater(showtimeEntity.getTheater());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        LocalTime[] newTimes = newTimesValues(showtimeEntity,formatter);


        for (ShowtimeEntity showtimeEntityInCinema:ShowtimeEntityInCinema){
            if(showtimeEntityInCinema.getId().equals(showtimeEntity.getId()))
                break;
            if (isOverlap(showtimeEntityInCinema,newTimes[0],newTimes[1],formatter)){}
                throw new IllegalStateException(String.format(
                        "The requested showtime conflicts with an existing showtime (ID: %d) in the cinema. " +
                                "Please choose a different time or adjust the current schedule to avoid overlap.",
                        showtimeEntityInCinema.getId()
                ));

        }
        return showtimeRepository.save(showtimeEntity);
    }

   @Override
    public ShowtimeEntity findById(Integer id) throws EntityNotFoundException{
       return showtimeRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Showtime not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteShowtime(Integer id) {
        bookingRepository.deleteAllByShowtimeId(id);
        showtimeRepository.deleteById(id);
    }
    private boolean isOverlap(ShowtimeEntity showtimeEntityInCinema,LocalTime newStartTime,LocalTime newEndTime,DateTimeFormatter formatter){
        OffsetDateTime existingStart = OffsetDateTime.parse(showtimeEntityInCinema.getStartTime(), formatter);
        OffsetDateTime existingEnd = OffsetDateTime.parse(showtimeEntityInCinema.getEndTime(), formatter);
        LocalTime existingStartTime = existingStart.toLocalTime();
        LocalTime existingEndTime = existingEnd.toLocalTime();
        // Check if the new showtime overlaps with an existing one
        if (newStartTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime))
           return true;
        return false;
    }

    private LocalTime[] newTimesValues(ShowtimeEntity showtimeEntity,DateTimeFormatter formatter){
        OffsetDateTime newStart = OffsetDateTime.parse(showtimeEntity.getStartTime(), formatter);
        OffsetDateTime newEnd = OffsetDateTime.parse(showtimeEntity.getEndTime(), formatter);
        return new LocalTime[] { newStart.toLocalTime(),newEnd.toLocalTime()};

    }

}
