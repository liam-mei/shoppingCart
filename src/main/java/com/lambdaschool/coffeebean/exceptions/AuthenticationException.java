package com.lambdaschool.coffeebean.exceptions;

import java.net.ProtocolException;

class AuthenticationException extends ProtocolException
{
    public AuthenticationException(long id, String message)
    {
        super("Authentication Error with ID " + id + " " + message);
    }
}
