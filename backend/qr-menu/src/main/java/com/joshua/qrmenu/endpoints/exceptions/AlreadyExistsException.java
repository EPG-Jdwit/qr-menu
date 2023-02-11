package com.joshua.qrmenu.endpoints.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception when a conflict occurs.
 */
@Getter
@AllArgsConstructor
public class AlreadyExistsException extends Exception {

    // Message to be shown
    private final String message;
}
