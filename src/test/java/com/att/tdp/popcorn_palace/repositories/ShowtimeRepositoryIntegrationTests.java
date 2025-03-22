package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class) // makes sure that everything is integrated and supported
public class ShowtimeRepositoryIntegrationTests {

    private ShowtimeRepository underTestA;
    private MovieRepository movieRepo;
    @Autowired
    public ShowtimeRepositoryIntegrationTests(ShowtimeRepository underTest, MovieRepository movieRepo){
        this.underTestA = underTest;
        this.movieRepo = movieRepo;
    }

    @Test
    public void testThatShowtimeCanBeCreatedAndRecalled()  {
        MovieEntity movieA = TestDataUtils.createTestMovieA();
        ShowtimeEntity showtimeEntity = TestDataUtils.creatTestShowtimeEntityA();
        MovieEntity saved = movieRepo.save(movieA);
        showtimeEntity.setMovie(saved);
        ShowtimeEntity saveed =  underTestA.save(showtimeEntity);

        Optional<ShowtimeEntity> res = underTestA.findById(showtimeEntity.getId());
        assertThat(res).isPresent();
        assertThat(res.get()).isEqualTo(saveed);

    }

}
