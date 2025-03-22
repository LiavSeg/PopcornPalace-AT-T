package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.database.TestDataUtils;
import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTests {
    @Test
    public void testThatObjectMapperCreateJsonObject() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MovieEntity movieA = TestDataUtils.createTestMovieB();
        String res = mapper.writeValueAsString(movieA);
        System.out.println(res);
    }

    @Test
    public void testThatObjectMapperCreateJavaObjectFromJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MovieEntity movieA = TestDataUtils.createTestMovieB();
        String res = mapper.writeValueAsString(movieA);
        MovieEntity a = mapper.readValue(res, MovieEntity.class);
        assertThat(a).isEqualTo(movieA);

    }
}

