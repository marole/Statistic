/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.api;


/**
 * The exception class representing application exceptions occurred in system.
 */
public class StatResourceApplicationException extends StatResourceException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic resource rest application exception.
     */
    public StatResourceApplicationException()
    {
        super();
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     *
     * @param message the message
     */
    public StatResourceApplicationException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public StatResourceApplicationException(final String message, final Throwable cause)
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
    public StatResourceApplicationException(final String message, final Throwable cause,
        final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic resource rest application exception.
     *
     * @param cause the cause
     */
    public StatResourceApplicationException(final Throwable cause)
    {
        super(cause);
    }
}
