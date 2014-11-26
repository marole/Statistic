/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.api;


/**
 * The exception class representing unpredictable problems occurred in the system.
 */
public class StatResourceRestUnpredictableException extends StatResourceRestException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic resource rest unpredictable exception.
     * 
     * @param message the message
     */
    public StatResourceRestUnpredictableException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic resource rest unpredictable exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public StatResourceRestUnpredictableException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic resource rest unpredictable exception.
     * 
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatResourceRestUnpredictableException(final String message, final Throwable cause,
        final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic resource rest unpredictable exception.
     * 
     * @param cause the cause
     */
    public StatResourceRestUnpredictableException(final Throwable cause)
    {
        super(cause);
    }
}
