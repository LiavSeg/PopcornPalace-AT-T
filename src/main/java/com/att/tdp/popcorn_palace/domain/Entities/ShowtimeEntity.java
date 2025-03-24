package com.att.tdp.popcorn_palace.domain.Entities;

import com.att.tdp.popcorn_palace.domain.Entities.validations.ValidStartEndTimes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "showtimes")
@ValidStartEndTimes
public class ShowtimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "showtime_id_sequence")
    private Integer id;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movie;
    private String theater;

    @NotNull(message = "Showtime must have a start time")
    private String startTime;

    @NotNull(message = "Showtime must have an end time")
    private String endTime;


    @PrePersist
    public void endTimeCalculation(){
        Integer duration = movie.getDuration();
        if (startTime == null && duration !=null){
            OffsetDateTime start = OffsetDateTime.parse(startTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            OffsetDateTime end = start.plusMinutes(duration);
            this.endTime = end.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }
}
