package com.lambdaschool.coffeebean.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException
{
    public BadRequestException(HttpStatus statusCode, String statusText)
    {
        super(statusCode + " - " + statusText);
    }
}
