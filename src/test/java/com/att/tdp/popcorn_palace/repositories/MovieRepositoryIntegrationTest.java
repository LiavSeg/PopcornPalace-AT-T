package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class) // makes sure that everything is integrated and supported
public class MovieRepositoryIntegrationTest {


    private MovieRepository underTest;

    @Autowired
    public MovieRepositoryIntegrationTest(MovieRepository underTest) { // constructor injection
        this.underTest = underTest;
    }

    @Test
    public void testThatMovieIsCreatedOnDatabaseAndRecalled() {
        MovieEntity movie = TestDataUtils.createTestMovieA();

        underTest.save(movie);
        Optional<MovieEntity> res = underTest.findById(movie.getId());
        assertThat(res).isPresent();
        assertThat(res.get()).isEqualTo(movie);
    }

    @Test
    public void testThatMultipleMovieIsCreatedOnDatabaseAndRecalled() {
        MovieEntity movieA = TestDataUtils.createTestMovieA();
        MovieEntity movieB = TestDataUtils.createTestMovieB();

        underTest.save(movieA);
        underTest.save(movieB);

        Iterable<MovieEntity> res = underTest.findAll();
        assertThat(res).
                hasSize(2).
                contains(movieA, movieB);
    }

    @Test
    public void testThatMovieIsUpdatedOnDatabase() {
        MovieEntity movieA = TestDataUtils.createTestMovieA();
        movieA.setTitle("Now This is a new title");
        underTest.save(movieA);
        Optional<MovieEntity> res = underTest.findById(movieA.getId());
        assertThat(res).isPresent();
        assertThat(res.get()).isEqualTo(movieA);

    }

    @Test
    public void testThatMovieIsDeletedOnDatabase() {
        MovieEntity movieA = TestDataUtils.createTestMovieA();
        underTest.save(movieA);
        Optional<MovieEntity> res = underTest.findById(movieA.getId());
        assertThat(res).isPresent();
        underTest.deleteById(movieA.getId());
        res = underTest.findById(movieA.getId());
        assertThat(res).isNotPresent();
    }

    @Test
    public void testThatMovieCanBeFoundByTitle() {
        MovieEntity movieA = TestDataUtils.createTestMovieA();
        underTest.save(movieA);
        Iterable<MovieEntity> foundMovie = underTest.findByTitle(movieA.getTitle());
        assertThat(foundMovie).contains(movieA);

    }


}
//
//@Test
//    public void testThatMovieIsUpdated() {
//        Movie movie = TestDataUtils.createTestMovie();
//        underTest.create(movie);
//        movie.setTitle(movie.getTitle() + " Updated");
//        underTest.update(movie.getId(), movie);
//        Optional<Movie> res = underTest.findOne(movie.getId());
//        assertThat(res).isPresent();
//
//}
//
//}
