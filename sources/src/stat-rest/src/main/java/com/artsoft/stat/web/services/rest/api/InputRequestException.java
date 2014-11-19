/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.api;


/**
 * Incorrect REST request data.
 */
public class InputRequestException extends StatResourceRestApplicationException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new incorrect input data exception.
     */
    public InputRequestException()
    {
        super();
    }


    /**
     * Instantiates a new incorrect input data exception.
     * 
     * @param message the message
     */
    public InputRequestException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new incorrect input data exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public InputRequestException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new incorrect input data exception.
     * 
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public InputRequestException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new incorrect input data exception.
     * 
     * @param cause the cause
     */
    public InputRequestException(final Throwable cause)
    {
        super(cause);
    }
}
