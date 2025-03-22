package com.att.tdp.popcorn_palace.domain.Entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data // Equals, toString, etc..
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "movies")

public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "movie_id_sequence")
    private Integer id;
    private String title;
    private String genre;
    private Integer duration;
    private Double rating;
    private Integer releaseYear;


    private void isValidInput(String title, String genre, int duration, double rating, int releaseYear) {

        if (title.isEmpty()){
            throw new IllegalArgumentException("Title is required");
        }
        else if (genre.isEmpty()){
            throw new IllegalArgumentException("Genre is required");
        }
        else if (duration <= 0 || duration > 240){
            throw new IllegalArgumentException("Duration must be between one minutes to four hours");
        }
        else if (rating < 0 || rating > 10){
            throw new IllegalArgumentException("Rating must be between 0.0 to 10.0");
        }
        else if (releaseYear < 1900 || releaseYear > 2025){
            throw new IllegalArgumentException("Year must be between 1900 to 2025");
        }
    }

}

