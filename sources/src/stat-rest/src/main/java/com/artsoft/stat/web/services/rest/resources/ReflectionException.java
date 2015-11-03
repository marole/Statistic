/*
 * ArtSoft 2014.
 */


package com.artsoft.stat.web.services.rest.resources;


/**
 * The reflection exception class.
 */
public class ReflectionException extends RuntimeException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new reflection exception.
     */
    public ReflectionException()
    {
        // Default exception constructor is needed.
    }


    /**
     * Instantiates a new reflection exception.
     *
     * @param message the message
     */
    public ReflectionException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new reflection exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public ReflectionException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new reflection exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public ReflectionException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new reflection exception.
     *
     * @param cause the cause
     */
    public ReflectionException(final Throwable cause)
    {
        super(cause);
    }
}
