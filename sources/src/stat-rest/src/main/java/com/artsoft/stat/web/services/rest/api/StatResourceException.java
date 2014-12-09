/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.api;


/**
 * The exception class representing exceptions occurred in the system.
 */
public class StatResourceException extends RuntimeException
{

    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic resource rest exception.
     */
    public StatResourceException()
    {
        super();
    }


    /**
     * Instantiates a new statistic resource rest exception.
     *
     * @param message the message
     */
    public StatResourceException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic resource rest exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public StatResourceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic resource rest exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatResourceException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic resource rest exception.
     *
     * @param cause the cause
     */
    public StatResourceException(final Throwable cause)
    {
        super(cause);
    }
}
