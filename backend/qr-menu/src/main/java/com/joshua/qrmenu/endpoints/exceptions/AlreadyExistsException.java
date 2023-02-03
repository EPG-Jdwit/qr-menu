package com.joshua.qrmenu.endpoints.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistsException extends Exception {

    private final String message;
}
