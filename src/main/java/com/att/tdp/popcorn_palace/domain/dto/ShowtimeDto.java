package com.att.tdp.popcorn_palace.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ShowtimeDto {
    private Integer id;
    private Double price;
    private MovieDto movie;
    private String theater;
    private String startTime;
    private String endTime;
}
