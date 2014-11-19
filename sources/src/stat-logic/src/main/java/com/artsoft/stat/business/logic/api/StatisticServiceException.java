/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.api;


/**
 * The base exception class of StatisticService bean.
 */
public class StatisticServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic service exception.
     */
    public StatisticServiceException()
    {
    }


    /**
     * Instantiates a new statistic service exception.
     * 
     * @param message the message
     */
    public StatisticServiceException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic service exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public StatisticServiceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic service exception.
     * 
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatisticServiceException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic service exception.
     * 
     * @param cause the cause
     */
    public StatisticServiceException(final Throwable cause)
    {
        super(cause);
    }
}
