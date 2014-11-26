/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.api;


/**
 * The exception class representing application exceptions occurred in system.
 */
public class StatResourceRestApplicationException extends StatResourceRestException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic resource rest application exception.
     */
    public StatResourceRestApplicationException()
    {
        super();
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     * 
     * @param message the message
     */
    public StatResourceRestApplicationException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public StatResourceRestApplicationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     * 
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatResourceRestApplicationException(final String message, final Throwable cause,
        final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     * 
     * @param cause the cause
     */
    public StatResourceRestApplicationException(final Throwable cause)
    {
        super(cause);
    }
}
