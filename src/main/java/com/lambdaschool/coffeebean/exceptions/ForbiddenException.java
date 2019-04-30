package com.lambdaschool.coffeebean.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException
{
    public ForbiddenException(HttpStatus statusCode, String statusText)
    {
        super(statusCode + " - " + statusText);
    }
}
