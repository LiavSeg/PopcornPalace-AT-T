package com.att.tdp.popcorn_palace.controllers;
import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
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

import static com.att.tdp.popcorn_palace.database.TestDataUtils.createTestMovieA;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookingControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ShowtimeService showtimeService;
    private MovieService movieService;
    @Autowired
    public BookingControllerIntegrationTests(MockMvc mockMvc ,ShowtimeService showtimeService,MovieService movieService){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.showtimeService = showtimeService;
        this.movieService = movieService;
    }

    @Test
    public void testThatCreatingBookingIsGenerating200HttpStatus() throws Exception {
        MovieEntity m  =createTestMovieA();
        MovieEntity saved = movieService.createMovie(m);
        ShowtimeEntity showtimeEntity = TestDataUtils.creatTestShowtimeEntityA();
        showtimeEntity.setMovie(saved);
        ShowtimeEntity savedS = showtimeService.createShowtime(showtimeEntity);
        BookingEntity bookingEntity = TestDataUtils.createTestBookingA();
        bookingEntity.setShowtimeId(savedS.getId());
        bookingEntity.setShowtimeId(saved.getId());
        String bookingJson = objectMapper.writeValueAsString(bookingEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
