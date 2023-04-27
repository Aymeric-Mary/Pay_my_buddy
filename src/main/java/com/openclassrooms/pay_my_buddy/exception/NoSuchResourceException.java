package com.openclassrooms.pay_my_buddy.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoSuchResourceException extends RuntimeException {
    private final Class resourceClass;
    private final Object id;

    public NoSuchResourceException(Class resourceClass) {
        super();

        this.resourceClass = resourceClass;
        id = null;
    }
}