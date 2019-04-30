package com.lambdaschool.coffeebean.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class HttpClientErrorException extends HttpStatusCodeException
{
    public HttpClientErrorException(HttpStatus statusCode, String statusText)
    {
        super(statusCode, statusText);
    }
}
