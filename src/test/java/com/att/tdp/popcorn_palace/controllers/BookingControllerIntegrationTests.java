package com.att.tdp.popcorn_palace.controllers;
import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookingControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BookingControllerIntegrationTests(MockMvc mockMvc){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatingBookingIsGenerating200HttpStatus() throws Exception {
        BookingEntity bookingEntity = TestDataUtils.createTestBookingA();
        String bookingJson = objectMapper.writeValueAsString(bookingEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
