package com.att.tdp.popcorn_palace.domain.Entities;

import com.att.tdp.popcorn_palace.domain.Entities.validations.ValidShowtime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "showtimes")
@ValidShowtime  // Applying custom validation

public class ShowtimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "showtime_id_sequence")
    private Integer id;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;
    private String theater;
    private String startTime;
    private String endTime;
}
