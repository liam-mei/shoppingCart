package com.lambdaschool.coffeebean.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException
{
    public HashMap<Object, Object> thing;

    public ConflictException(HttpStatus statusCode, String statusText)
    {
        super(statusCode + " - " + statusText);

    }
}
