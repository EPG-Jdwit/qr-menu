package com.joshua.qrmenu.endpoints.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception indicating invalid input.
 */
@Getter
@AllArgsConstructor
public class InputException extends Exception {

    /**
     * Message to be shown
     */
    private final String message;

    /**
     * Parameter which contains the invalid input.
     */
    private final String parameter;
}
