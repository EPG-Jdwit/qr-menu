package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;

import java.util.Optional;

public class AbstractService {

    /**
     * Extracts the value out of an Optional and throws a NotFoundException if no value was found.
     *
     * @param optional: The Optional containing the value.
     * @return : Value that was wrapped by the optional.
     * @throws NotFoundException : When the Optional does not contain a value.
     */
    protected static <S> S parseOptional(Optional<S> optional) throws NotFoundException {
        if (!optional.isPresent()) {
            throw new NotFoundException();
        }
        return optional.get();
    }
}
