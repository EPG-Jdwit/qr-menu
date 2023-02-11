package com.joshua.qrmenu.endpoints.util;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
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

    /**
     * An exception handler for endpoints throwing InputExceptions.
     *
     * @param ex : The InputException.
     * @return : The parameter and message of the exception wrapped in a ResponseEntity.
     */
    @ExceptionHandler(InputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> exception400(InputException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getParameter() + ":" + ex.getMessage());
    }

    /**
     * An exception handler for endpoints throwing AlreadyExistsExceptions.
     *
     * @param ex : The alreadyExistsException.
     * @return : The message of the exception wrapped in a ResponseEntity
     */
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> exception409(AlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    //TODO: 405 Method not allowed
}
