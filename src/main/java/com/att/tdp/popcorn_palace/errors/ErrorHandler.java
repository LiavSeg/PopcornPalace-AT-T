package com.att.tdp.popcorn_palace.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

public class ErrorHandler {
    private final Logger logger;
    public ErrorHandler(Class<?> c) {
        logger = LoggerFactory.getLogger(c);
    }

    public <T> ResponseEntity<?> notFound(T resource,Exception e,String path) {
        logger.error("Requested resource could not be found: Response Code: 404. Details: {}", e.getMessage());
        String currPath = path+"/"+resource;
        if (resource == "")
            currPath = path;
        ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),"NOT FOUND",currPath,e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    public <T> ResponseEntity<?> conflict(T resource,Exception e,String path) {
        String currPath = path+"/"+resource;
        if (resource == "")
            currPath = path;
        logger.error("Conflict detected: Response Code: 409. Details: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(),
                HttpStatus.CONFLICT.value(),"CONFLICT",currPath,e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
    public <T> ResponseEntity<?> badRequest(T resource,Exception e,String path) {
        String currPath = path+"/"+resource;
        if (resource == "")
            currPath = path;
        logger.error("Bad request: Response Code: 400. Details: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST",currPath,e.getMessage());
        System.out.print(errorResponse);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
