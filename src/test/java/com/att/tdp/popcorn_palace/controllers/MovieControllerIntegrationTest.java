package com.att.tdp.popcorn_palace.controllers;


import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private MovieService movieService;

    @Autowired
    MovieControllerIntegrationTest(MockMvc mockMvc,MovieService movieService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.movieService = movieService;
    }

    @Test
    public void testThatCreateMovieReturnsHttp200Created() throws Exception {
        MovieEntity movieEntity = TestDataUtils.createTestMovieA();
        movieEntity.setId(null);
        String movieJson = objectMapper.writeValueAsString(movieEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateMovieReturnsHttp200WhenMovieExists() throws Exception {

        MovieEntity movieEntity = TestDataUtils.createTestMovieA();
        MovieEntity savedEntity = movieService.createMovie(movieEntity);
        String title = savedEntity.getTitle();
        MovieDto movieDtoB = TestDataUtils.createTestMovieBdto();
        String movieJsonB = objectMapper.writeValueAsString(movieDtoB);
        mockMvc.perform(MockMvcRequestBuilders.put("/movies/update/{title}",title).
                contentType(MediaType.APPLICATION_JSON)
                .content(movieJsonB)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateReturns404WhenMovieDoesNotExist() throws Exception {
        MovieDto movieDto = TestDataUtils.createTestMovieBdto();
        String movieJson = objectMapper.writeValueAsString(movieDto);
        String title = "Does_not_Exist";
        mockMvc.perform(MockMvcRequestBuilders.put("/movies/update/{title}",title)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
//    @Test
//    public void testThatUpdateMovieUpdatesMovie() throws Exception {
//        MovieEntity movieEntity = TestDataUtils.createTestMovieA();
//        MovieEntity savedEntity = movieService.createMovie(movieEntity);
//
//        String title = savedEntity.getTitle();
//        MovieDto movieDtoB = TestDataUtils.createTestMovieBdto();
//        String movieJsonB = objectMapper.writeValueAsString(movieDtoB);
//
//        movieDtoB.setId(savedEntity.getId());
//        mockMvc.perform(MockMvcRequestBuilders.put("/movies/update/{title}",title)
//        .contentType(MediaType.APPLICATION_JSON)
//                .content(movieJsonB))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(movieDtoB.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(movieDtoB.getTitle()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(movieDtoB.getGenre()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(movieDtoB.getDuration()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(movieDtoB.getRating()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseYear").value(movieDtoB.getReleaseYear())
//        );
//    }

}
