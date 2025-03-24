package com.att.tdp.popcorn_palace.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private String info;
}
