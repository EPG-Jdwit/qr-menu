package com.joshua.qrmenu.endpoints.util;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Super Class for mutual methods and exceptions.
 */
public class BaseController {

    /**
     * An exception handler for endpoints throwing NotFoundExceptions.
     *
     * @param ex : The NotFoundException.
     * @return : The message of the exception wrapped in a ResponseEntity.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> exception404(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
