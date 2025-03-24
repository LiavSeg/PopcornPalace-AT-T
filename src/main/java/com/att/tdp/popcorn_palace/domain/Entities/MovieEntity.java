package com.att.tdp.popcorn_palace.domain.Entities;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "Movie genre can't be empty")
    @NotBlank(message = "Movie title can't be blank")
    private String title;

    @NotNull(message = "Movie genre can't be empty")
    @Size(min = 1, max = 50)
    private String genre;

    @Positive(message = "Movie duration must be a positive integer")
    private Integer duration;

    @Min(message = "Movie rating's minimum value is 0", value = 0)
    @Max(message = "Movie rating's maximum value is 10", value = 10)
    private Double rating;
    
    @Min(message = "Movie's release year can't be before 1888", value = 1888)
    @Max(message = "Movie's release year can't be in the future!",value = 2025)
    private Integer releaseYear;

}

