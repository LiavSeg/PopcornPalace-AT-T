package com.att.tdp.popcorn_palace.database;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class TestDataUtils {
    private TestDataUtils() {

    }

    public static MovieEntity createTestMovieA() {
        return MovieEntity.builder()
                .title("Movie")
                .genre("Action")
                .duration(120)
                .rating(9.2)
                .releaseYear(2004)
                .build();
    }

    public static MovieEntity createTestMovieB() {
        return MovieEntity.builder()
                .title("Movie")
                .genre("Action")
                .duration(120)
                .rating(9.2)
                .releaseYear(2004)
                .build();
    }

    public static MovieEntity createTestMovieForShow() {
        return MovieEntity.builder()
                .id(10)
                .title("Movie")
                .genre("Action")
                .duration(120)
                .rating(9.2)
                .releaseYear(2004)
                .build();
    }

    public static MovieDto createTestMovieAdto() {
        return MovieDto.builder()
                .id(1)
                .title("MovieA")
                .genre("Action")
                .duration(120)
                .rating(9.2)
                .releaseYear(2004)
                .build();
    }
    public static MovieDto createTestMovieBdto() {
        return MovieDto.builder()
                .id(549)
                .title("MovieUpdatedNow")
                .genre("Action")
                .duration(120)
                .rating(9.2)
                .releaseYear(2004)
                .build();
    }

    public static ShowtimeEntity creatTestShowtimeEntityA(){
        return ShowtimeEntity.builder()
            .price(30.2)
            .movie(null)
            .theater("Cinema City")
            .startTime(generateTimes(10))
            .endTime(generateTimes(12))
            .build();

    }

    private static String generateTimes(Integer hour) {
        if (hour == null){
            hour = 0;
        }
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime customDateTime = currentDate.withHour(hour).withMinute(0).withSecond(0).withNano(0);
        OffsetDateTime offsetDateTime = customDateTime.atOffset(ZoneOffset.UTC);
        return offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static BookingEntity createTestBookingA() {
        return BookingEntity.builder()
                .userId(UUID.randomUUID())
                .seatNumber(15)
                .build();
    }

}
