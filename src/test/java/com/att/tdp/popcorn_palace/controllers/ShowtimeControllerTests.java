package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static com.att.tdp.popcorn_palace.database.TestDataUtils.creatTestShowtimeEntityA;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ShowtimeControllerTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private MovieService movieService;
    private ShowtimeService showtimeService;

    @Autowired
    public ShowtimeControllerTests(MockMvc mockMvc,MovieService movieService,ShowtimeService showtimeService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @Test

    public void testThatCreateShowTimeGenerates200HttpsWhenCreated () throws Exception {
        MovieEntity movieEntity = TestDataUtils.createTestMovieA();
        MovieEntity savedMovieEntity=movieService.createMovie(movieEntity);
        ShowtimeDto test = TestDataUtils.creatTestShowtimeEntityADto();
        test.setMovieId(savedMovieEntity.getId());
        String jsonTest = objectMapper.writeValueAsString(test);
        System.out.println(jsonTest);
        mockMvc.perform(MockMvcRequestBuilders.post("/showtimes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTest)
                ).andExpect(MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetByIdGenerates200Https () throws Exception {
        ShowtimeEntity testShowtime = creatTestShowtimeEntityA();
        MovieEntity movieEntity = TestDataUtils.createTestMovieA();
        MovieEntity savedMovieEntity=movieService.createMovie(movieEntity);
        testShowtime.setMovie(savedMovieEntity);
        ShowtimeEntity savedShowTime = showtimeService.createShowtime(testShowtime);
        mockMvc.perform(MockMvcRequestBuilders.get("/showtimes/{id}",savedShowTime.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedShowTime.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(savedShowTime.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.theater").value(savedShowTime.getTheater()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value(savedShowTime.getStartTime()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value(savedShowTime.getEndTime()));
    }

    @Test
    public void testThatOverlappingShowtimesCantBeFound () throws Exception {
        ShowtimeEntity testA = creatTestShowtimeEntityA();
        MovieEntity movieEntity = TestDataUtils.createTestMovieA();
        movieService.createMovie(movieEntity);
        testA.setMovie(movieEntity);
        showtimeService.createShowtime(testA);

        ShowtimeDto testB = TestDataUtils.createTestShowtimeAdto();
        testB.setMovieId(movieEntity.getId());

        String jsonTest = objectMapper.writeValueAsString(testB);

        mockMvc.perform(MockMvcRequestBuilders.post("/showtimes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTest)
        ).andExpect(MockMvcResultMatchers.status().isConflict());

    }







//    @Test
//    public void testThatDeleteMovieGenerates200Https() throws Exception {
//        ShowtimeDto testShowtime = TestDataUtils.creatTestShowtimeEntityADto();
//        ShowtimeDto savedShowTime = showtimeService.createShowtime(testShowtime);
//        Integer showtimeId = savedShowTime.getId();
//        mockMvc.perform(MockMvcRequestBuilders.delete("/showtimes/{showtimeId}",showtimeId))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    @Test
    public void testThatDeleteMovieGenerates404HttpsWhenTryingToDeleteUnknownShowtime() throws Exception {
        Integer showtimeId = 100101;
        mockMvc.perform(MockMvcRequestBuilders.delete("/showtimes/{showtimeId}",showtimeId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


//    @Test
//    public void testThatDeleteMovieRemovesShowtime() throws Exception {
//        ShowtimeEntity testShowtime = creatTestShowtimeEntityA();
//        ShowtimeEntity savedShowTime = showtimeService.createShowtime(testShowtime);
//
//
//    }

}

